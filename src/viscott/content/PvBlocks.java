package viscott.content;

import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.TallBlock;

public class PvBlocks {
    public static Block
            /*Floor's*/
            densePlate1,densePlate2,densePlate3,densePlate4,
            densePlate5,densePlate6,densePlate7,densePlate8,
            damagedDensePlate,denseMetalWall,bariumWall,bariumPowder,

            /*Ore's*/
            erbiumOre,lithiumOre,zirconiumOre,platinumOre,

            /*Deposit's*/
            erbiumDepositLarge,lithiumDepositLarge,platinumDepositLarge,zirconiumDepositLarge,
                    erbiumDeposit,lithiumDeposit,platinumDeposit,zirconiumDeposit
    ;
            public static void load()
            {
                /*Floor's Start*/
                densePlate1 = new Floor("dense-panel-1",1){{localizedName = "Dense Panel 1";}};
                densePlate2 = new Floor("dense-panel-2",1){{localizedName = "Dense Panel 2";}};
                densePlate3 = new Floor("dense-panel-3",1){{localizedName = "Dense Panel 3";}};
                densePlate4 = new Floor("dense-panel-4",1){{localizedName = "Dense Panel 4";}};
                densePlate5 = new Floor("dense-panel-5",1){{localizedName = "Dense Panel 5";}};
                densePlate6 = new Floor("dense-panel-6",1){{localizedName = "Dense Panel 6";}};
                densePlate7 = new Floor("dense-panel-7",1){{localizedName = "Dense Panel 7";}};
                densePlate8 = new Floor("dense-panel-8",1){{localizedName = "Dense Panel 8";}};
                damagedDensePlate = new Floor("damaged-dense-plate",3);
                denseMetalWall = new StaticWall("dense-metal-wall"){{variants = 2; localizedName = "Dense Metal Wall";}};
                bariumWall = new StaticWall("barium-wall"){{variants = 2; localizedName = "Barium Wall";}};
                bariumPowder = new Floor("barium-powder",3){{localizedName = "Barium Powder";}};
                /*Floor's End*/
                /*Ore's Start*/
                erbiumOre = new OreBlock("erbium-ore",PvItems.erbium)
                {{
                    variants = 3;
                    localizedName = "Erbium Ore";
                }};
                lithiumOre = new OreBlock("lithium-ore",PvItems.lithium)
                {{
                    variants = 3;
                    localizedName = "Lithium Ore";
                }};
                platinumOre = new OreBlock("platinum-ore",PvItems.platinum)
                {{
                    variants = 3;
                    localizedName = "Platinum Ore";
                }};
                zirconiumOre = new OreBlock("zirconium-ore",PvItems.zirconium)
                {{
                    variants = 3;
                    localizedName = "Zirconium Ore";
                }};
                /*Ore's End*/
                /*Deposit's Start*/
                erbiumDepositLarge = new TallBlock("large-erbium-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 512;
                    localizedName = "Large Erbium Deposit";
                    itemDrop = PvItems.erbium;
                }};
                lithiumDepositLarge = new TallBlock("large-lithium-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 512;
                    localizedName = "Large Lithium Deposit";
                    itemDrop = PvItems.lithium;
                }};
                platinumDepositLarge = new TallBlock("large-platinum-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 512;
                    localizedName = "Large Platinum Deposit";
                    itemDrop = PvItems.platinum;
                }};
                zirconiumDepositLarge = new TallBlock("large-zirconium-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 512;
                    localizedName = "Large Zirconium Deposit";
                    itemDrop = PvItems.zirconium;
                }};
                erbiumDeposit = new TallBlock("erbium-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Erbium Deposit";
                    itemDrop = PvItems.erbium;
                }};
                lithiumDeposit = new TallBlock("lithium-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Lithium Deposit";
                    itemDrop = PvItems.lithium;
                }};
                platinumDeposit = new TallBlock("platinum-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Platinum Deposit";
                    itemDrop = PvItems.platinum;
                }};
                zirconiumDeposit = new TallBlock("zirconium-deposit")
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Zirconium Deposit";
                    itemDrop = PvItems.zirconium;
                }};
                /*Deposit's End*/
            }
}
