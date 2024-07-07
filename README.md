# FarmProtectForked
A Minecraft Plugin Forked from Jacklin213's FarmProtect 1.9.0

FarmProtect is a Paper plugin that makes sure NOBODY (including your self) makes farmland turn back to normal by either jumping on it or walking over it. It has some configurable features for people that like to tweek around with the configuration. 

##### MultiWorld Support
You can now choose to select which worlds you want to protect and which worlds you don't.
By default this feature is turned off. You can turn it on by either editing the config or using the command /fp toggle multiworld. You can add and remove worlds from this list at any time using /fp add , /fp remove respectively

##### ExplosionProtect Support
You can now protect your farmland from any sort of explosion. It is on by default by having this value in the config.                    
![image](https://github.com/woundedkoba/FarmProtectForked/assets/174161751/d4b3d351-54e2-43ff-ad29-8bc246090c76)

To turn it off edit the config or just use /fp toggle explosionprotect

---------------------------------------------------------------------------------

All credit to Jacklin213 for all original work. I only take credit for having updated this plugin for MC 1.21 and other items listed below.

Without going into full detail changes include but are not limited to:
- Upgraded to Java 21 (Jacklin213's FarmProtect 1.9.0 uses Java 8)
- Upgraded to Paper API 1.21-R0.1-SNAPSHOT (Jacklin213's FarmProtect 1.9.0 did not specify an API Version)
- Upgraded to Maven Compiler Plugin 3.13.0
- Removed the use of bStats and Metrics
- Removed the use of UpdateChecking/AutoUpdating
- Implemented the use of the Adventure Platform API (to replace the use of ChatColor from the Spigot API as this is deprecated for the Paper API) ; forgot to replace the use of ChatColor.translateAlternateColorCodes(), will do so in the next release
- Added some instances of @SuppressWarnings("deprecation") due to my use of the Paper API versus the Spigot API ; will resolve the deprecations in the next release
- BUGFIX: Material.CROPS and Material.SOIL are both deprecated for the Paper API. Replaced Material.CROPS with Material.WHEAT and Material.SOIL with Material.FARMLAND.











