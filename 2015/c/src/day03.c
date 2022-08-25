/*
 * Copyright 2021 Pietro F. Maggi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SANTA       (1)
#define ROBOT_SANTA (2)

int part1(char *filename) {
    // Open the file
    FILE *fp = fopen(filename, "r");
    if (!fp) {
        perror("File opening failed");
        return EXIT_FAILURE;
    }

    int x = 0;
    int y = 0;
    int max_x = 0;
    int max_y = 0;
    int min_x = 0;
    int min_y = 0;

    char map[200][200];
    memset(map, '.', 200*200);

    int pos_x = 100;
    int pos_y = 100;
    int count = 1;
    map[pos_x][pos_y] = 'Y';

    int c;
    while ((c = fgetc(fp)) != EOF) {
        if ('>' == (char)c) {
            pos_x++;
            x++;
        } else if ('<' == (char)c) {
            pos_x--;
            x--;
        } else if ('^' == (char)c) {
            pos_y++;
            y++;
        } else if ('v' == (char)c) {
            pos_y--;
            y--;
        }
        if (x > max_x) max_x = x;
        if (y > max_y) max_y = y;
        if (x < min_x) min_x = x;
        if (y < min_y) min_y = y;
        if (map[pos_x][pos_y] != 'Y') {
            map[pos_x][pos_y] = 'Y';
            count++;
        }
    }
    fclose(fp);

    printf("X -> Min: %d # Max: %d\n", min_x, max_x);
    printf("Y -> Min: %d # Max: %d\n", min_y, max_y);

    return count;
}

int part2(char *filename) {
    // Open the file
    FILE *fp = fopen(filename, "r");
    if (!fp) {
        perror("File opening failed");
        return EXIT_FAILURE;
    }

    int x = 0;
    int y = 0;
    int max_x = 0;
    int max_y = 0;
    int min_x = 0;
    int min_y = 0;

    int rs_x = 0;
    int rs_y = 0;
    int rs_max_x = 0;
    int rs_max_y = 0;
    int rs_min_x = 0;
    int rs_min_y = 0;

    char map[200][200];
    memset(map, '.', 200*200);

    int pos_x = 100;
    int pos_y = 100;
    int pos_x_rs = 100;
    int pos_y_rs = 100;
    int count = 1;
    int turn = SANTA;
    map[pos_x][pos_y] = 'Y';

    int c;
    while ((c = fgetc(fp)) != EOF) {
        if (turn == SANTA) {
            if ('>' == (char)c) {
                pos_x++;
                x++;
            } else if ('<' == (char)c) {
                pos_x--;
                x--;
            } else if ('^' == (char)c) {
                pos_y++;
                y++;
            } else if ('v' == (char)c) {
                pos_y--;
                y--;
            }
            if (x > max_x) max_x = x;
            if (y > max_y) max_y = y;
            if (x < min_x) min_x = x;
            if (y < min_y) min_y = y;
            if (map[pos_x][pos_y] != 'Y') {
                map[pos_x][pos_y] = 'Y';
                count++;
            }
            turn = ROBOT_SANTA;
        } else {
            if ('>' == (char)c) {
                pos_x_rs++;
                rs_x++;
            } else if ('<' == (char)c) {
                pos_x_rs--;
                rs_x--;
            } else if ('^' == (char)c) {
                pos_y_rs++;
                rs_y++;
            } else if ('v' == (char)c) {
                pos_y_rs--;
                rs_y--;
            }
            if (rs_x > rs_max_x) rs_max_x = rs_x;
            if (rs_y > rs_max_y) rs_max_y = rs_y;
            if (rs_x < rs_min_x) rs_min_x = rs_x;
            if (rs_y < rs_min_y) rs_min_y = rs_y;
            if (map[pos_x_rs][pos_y_rs] != 'Y') {
                map[pos_x_rs][pos_y_rs] = 'Y';
                count++;
            }
            turn = SANTA;
        }
    }
    fclose(fp);

    printf("X -> Min: %d # Max: %d\n", min_x, max_x);
    printf("Y -> Min: %d # Max: %d\n", min_y, max_y);
    printf("ROBOT SANTA\n");
    printf("X -> Min: %d # Max: %d\n", rs_min_x, rs_max_x);
    printf("Y -> Min: %d # Max: %d\n", rs_min_y, rs_max_y);

    return count;
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        printf("Please enter filename to input data:\n");
        printf("> %s <filename>\n", argv[0]);
        return EXIT_FAILURE;
    }

    printf("AoC2015 - Day03\n===============\n");
    printf("Total Houses Count for part 1: %d\n", part1(argv[1]));
    printf("Total Houses Count for part 2: %d\n", part2(argv[1]));

    return EXIT_SUCCESS;
}
