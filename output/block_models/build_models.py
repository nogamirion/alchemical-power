"""Rebuild the two cuboid block models; leaves textures and gameplay code alone."""
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
ASSETS = ROOT / 'src/main/resources/assets/alchemical_power'
TEXTURES = {k: f'alchemical_power:block/alchemical_machine_{k}'
            for k in ('iron', 'brass', 'recess', 'teal')}
TEXTURES.update(glass='minecraft:block/glass',
                liquid='alchemical_power:fluid/liquid_panakeia_still',
                particle=TEXTURES['iron'])

class Model:
    def __init__(self):
        self.elements = []

    def box(self, name, lo, hi, mat='iron', full_uv=False):
        dx, dy, dz = [b-a for a,b in zip(lo,hi)]
        faces = {}
        for face in ('north','south','east','west','up','down'):
            u,v = (dx,dz) if face in ('up','down') else ((dz,dy) if face in ('east','west') else (dx,dy))
            faces[face] = {'uv': [0,0,16,16] if full_uv else [0,0,u,v], 'texture': '#'+mat}
        self.elements.append({'name':name,'from':lo,'to':hi,'faces':faces})

    def ring(self, name, x0,z0,x1,z1,y0,y1,t=0.5,mat='brass'):
        self.box(name+' front',[x0,y0,z0],[x1,y1,z0+t],mat)
        self.box(name+' back',[x0,y0,z1-t],[x1,y1,z1],mat)
        self.box(name+' left',[x0,y0,z0+t],[x0+t,y1,z1-t],mat)
        self.box(name+' right',[x1-t,y0,z0+t],[x1,y1,z1-t],mat)

    def tank(self,name,x0,z0,x1,z1,y0,y1,level=None):
        self.box(name+' floor',[x0,y0,z0],[x1,y0+0.6,z1])
        if level is not None:
            self.box(name+' liquid',[x0+0.6,y0+0.6,z0+0.6],[x1-0.6,level,z1-0.6],'liquid',True)
        # Thin cutout panes have transparent centers, revealing real inner geometry.
        self.box(name+' front glass',[x0+0.4,y0+0.6,z0+0.2],[x1-0.4,y1-0.6,z0+0.3],'glass',True)
        self.box(name+' back glass',[x0+0.4,y0+0.6,z1-0.3],[x1-0.4,y1-0.6,z1-0.2],'glass',True)
        self.box(name+' left glass',[x0+0.2,y0+0.6,z0+0.4],[x0+0.3,y1-0.6,z1-0.4],'glass',True)
        self.box(name+' right glass',[x1-0.3,y0+0.6,z0+0.4],[x1-0.2,y1-0.6,z1-0.4],'glass',True)
        for x in (x0,x1-0.6):
            for z in (z0,z1-0.6):
                self.box(name+' upright',[x,y0+0.6,z],[x+0.6,y1-0.6,z+0.6])
        self.box(name+' lid',[x0,y1-0.6,z0],[x1,y1,z1])
        self.ring(name+' lower band',x0-0.1,z0-0.1,x1+0.1,z1+0.1,y0+0.2,y0+0.5,0.3)
        self.ring(name+' upper band',x0-0.1,z0-0.1,x1+0.1,z1+0.1,y1-0.5,y1-0.2,0.3)

    def save(self,stem):
        obj={'parent':'minecraft:block/block','render_type':'minecraft:cutout',
             'textures':TEXTURES,'elements':self.elements,
             'display':{'gui':{'rotation':[30,225,0],'scale':[0.625]*3},
                        'fixed':{'rotation':[0,180,0],'scale':[0.5]*3}}}
        (ASSETS/'models/block'/f'{stem}.json').write_text(json.dumps(obj,indent=2)+'\n',encoding='utf-8')
        print(stem, len(self.elements), 'cuboids')

e=Model()
e.box('plinth',[0.5,0,0.5],[15.5,2.5,15.5])
e.ring('plinth trim',0.4,0.4,15.6,15.6,0.5,0.9)
# Keep the brass top above the plinth top (Y=2.5) to avoid coplanar faces.
e.ring('deck rim',0.4,0.4,15.6,15.6,2.1,2.55)
e.box('front service plate',[2,0.9,0.3],[6.5,2.0,0.5],'recess')
e.box('service latch',[3.8,1.0,0.1],[4.7,1.8,0.3],'brass')
for y in (0.9,1.5):
    e.box('front vent',[10,y,0.3],[13.5,y+0.3,0.5],'recess')
e.tank('extraction column',1.5,4,7.5,11.5,2.5,12)
e.box('hopper neck',[3,12,5.5],[6,12.7,10])
e.box('hopper lower step',[2.3,12.7,4.8],[6.7,13.4,10.7])
e.box('hopper body',[1.0,13.4,3.5],[8,15,12])
e.ring('hopper brass band',0.9,3.4,8.1,12.1,13.7,14.1,0.3)
e.box('hopper top recess',[2.3,15,4.8],[6.7,15.1,10.7],'recess')
e.ring('hopper lip',2.0,4.5,7.0,11.0,15.05,15.5,0.4,mat='iron')
e.box('hopper emblem',[4,14.0,3.2],[5,14.8,3.5],'brass')
e.tank('collection vessel',9,4.5,14.5,11,2.5,8.7)
e.box('pipe from column',[7.5,10,7],[8.5,11,8])
e.box('pipe crossbar',[8.5,10,7],[12.5,11,8])
e.box('pipe down',[11.5,8.7,7],[12.5,10,8])
e.box('pipe collar',[9.3,9.8,6.8],[10,11.2,8.2],'brass')
e.box('tank inlet collar',[11.2,8.7,6.7],[12.8,9.3,8.3],'brass')
e.box('rear supply socket',[3.2,0.9,15.5],[5.8,2,16],'teal')
e.save('panakeia_extractor')

r=Model()
r.box('reactor foundation',[0.5,0,0.5],[15.5,2.5,15.5])
r.ring('foundation brass rim',0.4,0.4,15.6,15.6,0.5,0.9)
r.tank('reaction chamber',2.5,2.5,13.5,13.5,2.5,13.4)
for x in (0.5,13.5):
    for z in (0.5,13.5):
        r.box('corner pillar',[x,2.5,z],[x+2,13.5,z+2])
        r.box('lower corner clasp',[x-0.1,2.0,z-0.1],[x+2.1,3.1,z+2.1],'brass')
        r.box('upper corner clasp',[x-0.1,12.4,z-0.1],[x+2.1,13.5,z+2.1],'brass')
        r.box('cap rivet',[x+0.45,14.5,z+0.45],[x+1.55,14.9,z+1.55],'brass')
r.ring('lower chamber brace',0.6,0.6,15.4,15.4,3.1,3.65,0.5)
r.ring('upper chamber brace',0.6,0.6,15.4,15.4,11.7,12.25,0.5)
r.box('lid',[0.5,13.5,0.5],[15.5,14.5,15.5])
r.ring('lid inlay',2,2,14,14,14.5,14.57,0.25)
r.box('feed recess',[6,14.5,6],[10,14.56,10],'recess')
r.ring('feed mouth',5.5,5.5,10.5,10.5,14.55,15.3,0.5)
for lo,hi in (([7.8,14.5,2],[8.2,14.57,5.5]),([7.8,14.5,10.5],[8.2,14.57,14]),
              ([2,14.5,7.8],[5.5,14.57,8.2]),([10.5,14.5,7.8],[14,14.57,8.2])):
    r.box('top radial trace',lo,hi,'brass')
r.box('output recess',[6,0.8,0.3],[10,2.25,0.5],'recess')
r.box('output lintel',[5.6,2.25,0],[10.4,2.7,0.5],'brass')
r.box('output sill',[5.6,0.4,0],[10.4,0.8,0.5],'brass')
for x in (5.6,10):
    r.box('output jamb',[x,0.8,0],[x+0.4,2.25,0.5],'brass')
r.box('side inlet',[13.5,6.5,6],[15.2,9.5,10])
r.box('inlet brass flange',[15.2,6.7,6.2],[15.7,9.3,9.8],'brass')
r.box('inlet aperture',[15.7,7.2,6.7],[16,8.8,9.3],'teal')
r.save('alchemical_reactor')
