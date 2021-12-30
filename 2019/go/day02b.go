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
	var memoryBase [200]int
	var value int
	i := 0

	for {
		if n, _ := fmt.Fscanf(file, "%d,", &value); n != 1 {
			break
		}
		memoryBase[i] = value
		i++
	}

	for noun := 0; noun < 100; noun++ {
		for verb := 0; verb < 100; verb++ {
			memory = memoryBase
			memory[1] = noun
			memory[2] = verb

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
					pointer += 4
				case 2:
					posMul1 := memory[pointer+1]
					posMul2 := memory[pointer+2]
					posResult := memory[pointer+3]
					data := memory[posMul1] * memory[posMul2]
					memory[posResult] = data
					memory[pointer+3] = data
					pointer += 4
				case 99:
					done = true
				default:
					fmt.Println("PANIC")
					os.Exit(1)
				}
			}

			if memory[0] == 19690720 {
				fmt.Printf("FOUND %04d --> ", memory[0])
				fmt.Printf("Noun = %d, Verb = %d |-->> Result = %d\n", noun, verb, (noun*100 + verb))
				os.Exit(0)
			}
		}
	}

}
