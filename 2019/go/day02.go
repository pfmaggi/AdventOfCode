package main

import (
	"fmt"
	"log"
	"os"
)

func main() {
	file, err := os.Open("../input/day02.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	var memory [200]int
	var value int
	i := 0

	for {
		if n, _ := fmt.Fscanf(file, "%d,", &value); n != 1 {
			break
		}
		fmt.Printf("value %03d: %04d\n", i, value)

		memory[i] = value
		i++
	}
	fmt.Println("count:", i)

	// Fix 1202
	memory[1] = 12
	memory[2] = 02

	fmt.Printf("\n-Init state- ")
	for count := 0; count < i; count++ {
		fmt.Printf("%04d - ", memory[count])
	}
	fmt.Printf("\n\n")

	pointer := 0
	var op int
	for done := false; !done && pointer < i; {
		op = memory[pointer]
		switch op {
		case 1:
			posAdd1 := memory[pointer+1]
			posAdd2 := memory[pointer+2]
			posResult := memory[pointer+3]
			data := memory[posAdd1] + memory[posAdd2]
			memory[posResult] = data
			fmt.Printf("%04d + %04d = %04d into position %04d\n", memory[posAdd1], memory[posAdd2], data, posResult)
			pointer += 4
		case 2:
			posMul1 := memory[pointer+1]
			posMul2 := memory[pointer+2]
			posResult := memory[pointer+3]
			data := memory[posMul1] * memory[posMul2]
			memory[posResult] = data
			fmt.Printf("%04d * %04d = %04d into position %04d\n", memory[posMul1], memory[posMul2], data, posResult)
			memory[pointer+3] = data
			pointer += 4
		case 99:
			fmt.Printf("END PROGRAM\n")
			done = true
		default:
			fmt.Println("PANIC")
		}
		// fmt.Printf("\n-Step %04d - ", pointer)
		// for count := 0; count < i; count++ {
		// 	fmt.Printf("%04d - ", memory[count])
		// }
	}

	fmt.Printf("\nResult = %04d\n", memory[0])

	// for count := 0; count < i; count++ {
	// 	fmt.Printf("%d - ", memory[count])
	// }
	// fmt.Println("-")

	// for count := 0; count < i; count++ {
	// 	fmt.Printf("%04d", memory[count])
	// }

	// scanner := bufio.NewScanner(file)
	// sum := 0
	// fuel := 0
	// totalFuel := 0
	// for scanner.Scan() {
	// 	mass, err := strconv.Atoi(scanner.Text())
	// 	if err != nil {
	// 		log.Fatal(err)
	// 		break
	// 	}
	// 	fuel = int(mass/3) - 2

	// 	sum += fuel
	// 	for ; fuel > 0; fuel = int(fuel/3) - 2 {
	// 		totalFuel += fuel

	// 	}
	// }

	// println("Part 1: ", sum, " - Part 2: ", totalFuel)
}
