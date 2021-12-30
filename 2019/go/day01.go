package main

import (
	"bufio"
	"log"
	"os"
	"strconv"
)

func main() {
	file, err := os.Open("../input/day01.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	sum := 0
	fuel := 0
	totalFuel := 0
	for scanner.Scan() {
		mass, err := strconv.Atoi(scanner.Text())
		if err != nil {
			log.Fatal(err)
			break
		}
		fuel = int(mass/3) - 2

		sum += fuel
		for ; fuel > 0; fuel = int(fuel/3) - 2 {
			totalFuel += fuel

		}
	}

	println("Part 1: ", sum, " - Part 2: ", totalFuel)
}
