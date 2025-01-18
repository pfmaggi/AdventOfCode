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

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
  if (argc < 2) {
    printf("Please enter filename to input data:\n");
    printf("> %s <filename>\n", argv[0]);
    return EXIT_FAILURE;
  }

  FILE *fp = fopen(argv[1], "r");
  if (!fp) {
    perror("File opening failed");
    return EXIT_FAILURE;
  }
  long total_paper = 0;
  long total_ribbon = 0;
  int c;
  int w, h, l;
  while ((c = fscanf(fp, "%dx%dx%d[^\n]", &w, &h, &l)) != EOF) {
    int b1 = w * h;
    int b2 = w * l;
    int b3 = h * l;
    int min = (b1 < b2) ? b1 : b2;
    min = (min < b3) ? min : b3;
    int max = (w > h) ? w : h;
    max = (max > l) ? max : l;

    total_paper += min + 2 * (b1 + b2 + b3);
    total_ribbon += 2 * (w + h + l - max) + (w * h * l);
  }
  fclose(fp);

  printf("AoC2015 - Day02\n===============\n");
  printf("total paper = %ld\n", total_paper);
  printf("total ribbon = %ld\n", total_ribbon);

  return EXIT_SUCCESS;
}
