# Clockwork Phase: Rewound

## About the Mod


Clockwork Phase was a mod made by Lumaceon. It ran on the Forge platform
and was released for Minecraft version 1.7.10. I first played with the mod
like I'm sure many others did – in the FTB modpack “Horizons: Daybreaker.”

Lumaceon released the mod under the GPL v2, a license that permits
distribution and modification as long as the new work is also licensed under
GPLv2.

“Clockwork Phase: Rewound” is a fork of and spiritual successor to Clockwork
Phase. I started work in December of 2022, and am releasing my code and art
assets under the terms of the original GPLv2 license.

Here's to another great era of Clockwork Phase!

## Setup

Note that the mod ships with data generation code. You will need to run the
`runDatagenClient` gradle task to generate the various JSON files that make
the mod work properly – otherwise, everything will be black-and-magenta,
recipes won't work, dogs and cats will live together, etc.

## Data Packs & Mod Compat

Data packs can modify the Clockwork Attributes of a component, for example, 
making the brass gears provide more speed, quality, or memory. See 
`test_data/cpr/clockwork_attribute_modifiers/` for examples, and 
`schema/clockwork_attribute_modifier.json` for a JSON schema.

