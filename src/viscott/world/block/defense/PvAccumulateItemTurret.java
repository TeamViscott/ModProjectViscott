package viscott.world.block.defense;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Strings;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;

public class PvAccumulateItemTurret extends PvItemTurret{
        public float acceleratedDelay = 120, acceleratedBonus = 1.5f;
        public int acceleratedSteps = 1;

        public float burnoutDelay = 240, cooldownDelay = 120;
        public boolean burnsOut = true;

        public PvAccumulateItemTurret(String name){
            super(name);
        }


        @Override
        public void setBars(){
            super.setBars();
            if(acceleratedBonus == 1){
                if(burnsOut){
                    addBar("aj-heat", (ItemAcceleratedTurretBuild entity) -> new Bar(
                            () -> Core.bundle.format("bar.aj-heat", Strings.autoFixed(entity.accelBoost * 100f, 2)),
                            () -> entity.accelCount > acceleratedSteps ? Pal.remove : Color.orange,
                            entity::boostf
                    ));
                }
            }else{
                addBar("aj-phases", (ItemAcceleratedTurretBuild entity) -> new Bar(
                        () -> Core.bundle.format("bar.aj-phases", Strings.autoFixed(entity.accelBoost * 100f, 2)),
                        () -> entity.accelCount > acceleratedSteps ? Pal.remove : Pal.techBlue,
                        entity::boostf
                ));
            }

        }

        public class ItemAcceleratedTurretBuild extends ItemTurretBuild {
            public float accelBoost, accelCounter;
            public int accelCount;

            @Override
            public void updateTile() {
                super.updateTile();


                if (accelCount > acceleratedSteps) { //if charged accel is greater than steps.
                    accelCounter += edelta();
                    if (accelCounter >= cooldownDelay) {
                        coolantMultiplier = 5;
                        super.updateCooling();
                        accelCount = 0;
                        accelBoost = 1;
                        accelCounter %= cooldownDelay;
                    }
                } else if (isShooting()) {
                    accelCounter += edelta();
                    if (accelCount < acceleratedSteps && accelCounter >= acceleratedDelay) {
                        accelBoost += (acceleratedBonus - 1);
                        accelCount++;
                        accelCounter %= acceleratedDelay;
                    } else if (burnsOut && accelCounter >= burnoutDelay) {
                        accelBoost = 0;
                        coolantMultiplier = 0;
                        accelCount++;
                        accelCounter %= burnoutDelay;
                    }
                } else { // if not shooting
                    accelCount = 0;
                    accelCounter = 0;
                    accelBoost = 1;
                    coolantMultiplier = 5;
                    super.updateCooling();
                }
            }

            @Override
            protected void updateReload() {
                float multiplier = hasAmmo() ? peekAmmo().reloadMultiplier : 1f;
                reloadCounter += delta() * multiplier * accelBoost * baseReloadSpeed();

                reloadCounter = Math.min(reloadCounter, reload);
            }

            public float boostf() {
                if (accelCount > acceleratedSteps) return 1 - (accelCounter / cooldownDelay);
                return Mathf.clamp((float) accelCount / acceleratedSteps);
            }
        }
}
