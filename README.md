## AntSimulation


## Inspiration
I was watching ants crawl across the ground, and even though the ground had no apparent path, the ants would always walk along in the exact same pattern. This inspired me to look into how ants always seemed to know where they were going despite no visible path. This introduced me to how ants use pheromones to track to food and home. I decided to implement this in my project. The idea was to create relatively simple rules among all of the ants which in turn would lead to a collective intelligence when many ants were spawned together.

## Pattern Creation
Timelapse of pattern creation from ants with music: https://youtu.be/OgPaR_nyR2E

Video of implosion behavior produced by colony: https://youtu.be/7pEteR24OeY

## Food Implementation

Below are images from the food implementation aspect of the project. The block of green pixels represents food. The large yellow block of pixels represents an obstacle. The pink circle in the middle represents the ant colony home, and the number ontop of it is the amount of food they have brought back to their colony.

# Key

Ants
- Light Blue Ant: Ant without food
- Dark Blue Ant: Ant with food
- Light Blue Path: Home pheromones
- Red Path: Food pheromones

Environment
- Green: Food
- Yellow: Obstacle
- Pink: Ant Colony Home
- Number: Amount of food obtained

# Initial Release
The ants are all released from the center at once (the ant colony home).
<img width="1035" alt="Screen Shot 2022-01-18 at 4 34 32 PM" src="https://user-images.githubusercontent.com/73970997/150022578-3b20f299-f8ee-46e0-9207-622c6e43ece0.png">

# Path Development
Ants discover the food for the first time.
<img width="1041" alt="Screen Shot 2022-01-18 at 4 42 08 PM" src="https://user-images.githubusercontent.com/73970997/150023407-7b5eec2a-47d4-4d67-96d5-47a698a1de74.png">

Initial paths start to form.
<img width="1041" alt="Screen Shot 2022-01-18 at 4 42 57 PM" src="https://user-images.githubusercontent.com/73970997/150023419-40f496de-253b-435e-a33d-b2e0208606e0.png">

The paths become more solidifed.
<img width="1032" alt="Screen Shot 2022-01-18 at 4 30 51 PM" src="https://user-images.githubusercontent.com/73970997/150022729-821cabdc-4051-4cea-87bd-6eef589fa7bd.png">

# Path Distance Reduced
Overtime, the paths are optimized to reduce distance. They tighten up and the ants walk past the blockade more efficiently.
<img width="1034" alt="Screen Shot 2022-01-18 at 4 32 55 PM" src="https://user-images.githubusercontent.com/73970997/150022815-5b70d035-f318-42c9-9ee0-45ab5953e904.png">
