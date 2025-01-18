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

  // Open the file
  FILE *fp = fopen(argv[1], "r");
  if (!fp) {
    perror("File opening failed");
    return EXIT_FAILURE;
  }
  long count = 0;
  long position = 0;
  long i = 0;
  int c;
  while ((c = fgetc(fp)) != EOF) {
    i++;
    if ('(' == (char)c) count++;
    if (')' == (char)c) count--;
    if ((0 == position) && (count < 0)) position = i;
  }
  fclose(fp);

  printf("AoC2015 - Day01\n===============\n");
  printf("Final floor is: %ld\n", count);
  printf("Entered the basement at position: %ld\n", position);

  return EXIT_SUCCESS;
}
