"""Static orthographic inspection of the actual JSON geometry and texture UVs.

This is a software preview, not Minecraft lighting or a client rendering test.
"""
import io
import json
import zipfile
from pathlib import Path
import numpy as np
from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[2]
ASSETS = ROOT / 'src/main/resources/assets'
OUT = Path(__file__).resolve().parent
CLIENT = Path.home()/'.gradle/caches/forge_gradle/minecraft_repo/versions/1.20.1/client.jar'

def texture(resource):
    namespace, relative = resource.split(':')
    name = f'assets/{namespace}/textures/{relative}.png'
    if namespace == 'minecraft':
        with zipfile.ZipFile(CLIENT) as jar:
            img = Image.open(io.BytesIO(jar.read(name))).convert('RGBA')
    else:
        img = Image.open(ASSETS/namespace/'textures'/f'{relative}.png').convert('RGBA')
    # Animated textures: inspect frame zero, matching default square frame size.
    return np.asarray(img.crop((0,0,img.width,img.width)))

def project(vertices):
    v = np.asarray(vertices,dtype=float)
    return np.column_stack((300+(v[:,0]+v[:,2]-16)*0.8660254*18,
                            300+(0.5*(v[:,0]-v[:,2])-v[:,1]+8)*18))

def render(stem):
    model=json.loads((ASSETS/'alchemical_power/models/block'/f'{stem}.json').read_text())
    textures={k:texture(v) for k,v in model['textures'].items()}
    canvas=np.full((600,600,4),[39,43,49,255],dtype=np.uint8)
    depth=np.full((600,600),-np.inf)
    for element in model['elements']:
        x0,y0,z0=element['from']; x1,y1,z1=element['to']
        faces={
            'north':([[x1,y1,z0],[x0,y1,z0],[x1,y0,z0]],0.88),
            'east': ([[x1,y1,z1],[x1,y1,z0],[x1,y0,z1]],0.72),
            'up':   ([[x0,y1,z0],[x1,y1,z0],[x0,y1,z1]],1.0)
        }
        for face,(vertices,shade) in faces.items():
            if face not in element['faces']: continue
            definition=element['faces'][face]
            tex=textures[definition['texture'][1:]]
            p=project(vertices)
            fourth=p[1]+p[2]-p[0]
            extent=np.vstack((p,fourth))
            left,top=np.maximum(0,np.floor(extent.min(axis=0)).astype(int))
            right,bottom=np.minimum(599,np.ceil(extent.max(axis=0)).astype(int))
            if right<left or bottom<top: continue
            yy,xx=np.mgrid[top:bottom+1,left:right+1]
            basis=np.column_stack((p[1]-p[0],p[2]-p[0]))
            if abs(np.linalg.det(basis))<1e-8: continue
            st=np.linalg.inv(basis) @ np.stack((xx+0.5-p[0,0],yy+0.5-p[0,1])).reshape(2,-1)
            s,t=st.reshape(2,*xx.shape)
            inside=(s>=0)&(s<=1)&(t>=0)&(t<=1)
            u0,v0,u1,v1=definition['uv']
            u=np.clip(((u0+s*(u1-u0))*tex.shape[1]/16).astype(int),0,tex.shape[1]-1)
            v=np.clip(((v0+t*(v1-v0))*tex.shape[0]/16).astype(int),0,tex.shape[0]-1)
            sampled=tex[v,u].copy()
            sampled[:,:,:3]=(sampled[:,:,:3].astype(float)*shade).astype(np.uint8)
            positions=np.asarray(vertices)
            depths=positions[:,0]+positions[:,1]-positions[:,2]
            d=depths[0]+s*(depths[1]-depths[0])+t*(depths[2]-depths[0])
            target_depth=depth[top:bottom+1,left:right+1]
            visible=inside&(sampled[:,:,3]>=128)&(d>target_depth)
            canvas[top:bottom+1,left:right+1][visible]=sampled[visible]
            target_depth[visible]=d[visible]
    image=Image.fromarray(canvas)
    ImageDraw.Draw(image).text((18,575),f'{stem} | JSON model preview (not in-game)',fill=(210,214,220))
    image.save(OUT/f'{stem}_preview.png')
    return image

if __name__ == '__main__':
    images=[render(stem) for stem in ('panakeia_extractor','alchemical_reactor')]
    sheet=Image.new('RGB',(1200,600))
    for i,img in enumerate(images): sheet.paste(img,(i*600,0))
    sheet.save(OUT/'models_preview.png')
