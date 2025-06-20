FarmProtectForked Change Log
-
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

FarmProtectForked v0.0.2
- Added documentation to include: Change Log, Known Issues, and Planned Updates
- Upgraded to Paper API 1.21.5-R0.1-SNAPSHOT
- Upgraded to Maven Compiler Plugin 3.14.0
- Replaced the use of ChatColor.translateAlternateColorCodes()
  - ChatColor.translateAlternateColorCodes() does not exist in Adventure. 
  - Now use: LegacyComponentSerializer.legacy(altChar).deserialize(input) when deserializing a legacy string.
- Refactored the code to resolve various IDE warnings and improve overall code quality, including cleanup of redundant expressions and adherence to best practices.


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
FarmProtectForked v0.0.1

Without going into full detail changes include but are not limited to:
- Upgraded to Java 21 (Jacklin213's FarmProtect 1.9.0 uses Java 8)
- Upgraded to Paper API 1.21-R0.1-SNAPSHOT (Jacklin213's FarmProtect 1.9.0 did not specify an API Version)
- Upgraded to Maven Compiler Plugin 3.13.0
- Removed the use of bStats and Metrics
- Removed the use of UpdateChecking/AutoUpdating
- Implemented the use of the Adventure Platform API (to replace the use of ChatColor from the Spigot API as this is deprecated for the Paper API) ; forgot to replace the use of ChatColor.translateAlternateColorCodes(), will do so in the next release
- Added some instances of @SuppressWarnings("deprecation") due to my use of the Paper API versus the Spigot API ; will resolve the deprecations in the next release
- BUGFIX: Material.CROPS and Material.SOIL are both deprecated for the Paper API. Replaced Material.CROPS with Material.WHEAT and Material.SOIL with Material.FARMLAND.