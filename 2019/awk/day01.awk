{
    fuel = int($1/3) - 2
    sum += fuel
    while (fuel > 0) {
        totalFuel += fuel
        fuel = int(fuel/3)-2
    }
} END { print "Part 1: ",sum, " - Part 2: ", totalFuel }
