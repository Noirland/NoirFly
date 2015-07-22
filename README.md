# NoirFly #

Configurable plugin that allows for player flight in a non-overpowered way.

* /fly command, allow for either permanent or temporary flight
* Drinkable potions for single player use
* Splashable potions for all players in splash range
* If available, uses XP bar as a timer for the flight
* Safely lands any players without fall damage

## Configuration ##

```
command: # Temporary flight via /fly command
  fly-time: 30 # Time, in seconds, to allow flight
  cooldown: 10 # Cooldown after flight is disabled
permanent: # Permanent flight via /fly command
  cooldown: 10
potion: # Temporary flight via drinkable potion
  fly-time: 60
  cooldown: 0
splash-potion: # Temporary flight via splash potion
  fly-time: 120
  cooldown: 0
```

## Commands ##

`/fly`

Toggles flight mode, as long as player has either `fly` or `permafly` permissions

## Permissions ##

`noirfly.fly` - Allows for temporary flight using `/fly` command

`noirfly.permafly` - Allows permanent flight using `/fly` command