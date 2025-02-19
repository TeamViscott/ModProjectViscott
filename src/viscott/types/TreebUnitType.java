package viscott.types;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.type.*;

/** @author EyeOfDarkness */
public class TreebUnitType extends PvUnitType{
    public TextureRegion[] branchRegions;
    public TextureRegion branchEndRegion;
    public float bDamage = 6, bLength = 6,bReload = 20;

    public TreebUnitType(String name){
        super(name);
        constructor = TreebUnit::new;
    }

    @Override
    public void load(){
        super.load();
        branchRegions = new TextureRegion[3];

        for(int i = 0; i < 3; i++){
            branchRegions[i] = Core.atlas.find(name + "-branch-" + i);
        }
        branchEndRegion = Core.atlas.find(name + "-branch-end");
    }
}
