This repo is a program to find the highest possible stack of boxes from a file of random shaped boxes. The boxes can be stacked in any order or orientation as long as they meet the condition that every box must sit on the top face of another box that is larger than its bottom face. The program uses a simulated annealing-based approach to solve this problem.

The program takes a file of boxes and the number of iterations to run on the file.

For best results:
| Box file | Iterations | Max tower height | Time taken |
| rand0050 |   2000000  |       526        | 12 seconds |
| rand0100 |   5000000  |      1148        | 26 seconds |
| rand1000 |   1000000  |      15229       | 56 seconds |
