# Clockwork Phase: Rewound

## Setup

Note that the mod ships with data generation code. You will need to run the `runDatagenClient` gradle task to generate 
the various JSON files that make the mod work properly – otherwise, everything will be black-and-magenta, recipes won't 
work, dogs and cats will live together, etc.

## Data Packs & Mod Compat

Data packs can modify the Clockwork Attributes of a component, for example, making the brass gears provide more speed, 
quality, or memory. See `test_data/cpr/clockwork_attribute_modifiers/` for examples, and 
`schema/clockwork_attribute_modifier.json` for a JSON schema.

