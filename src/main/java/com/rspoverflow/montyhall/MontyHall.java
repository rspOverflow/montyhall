/*
 * Copyright 2025 rspOverflow (nanoHeap)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rspoverflow.montyhall;

import java.security.SecureRandom;


public class MontyHall {
  /* This is overkill. But I'm totally for it. */
  /* Create once since PRNG seeding in general is expensive */
  private static SecureRandom csprng = new SecureRandom();

  /**
   * Random number generator with that outputs between interval [min, max]
   * 
   * @param min Smallest number that can be outputted
   * @param max Biggest number that can be outputted
   * @return A random number between interval [min, max]
   */
  public static int randInt(int min, int max) {
    return csprng.nextInt(max - min + 1) + min;
  }

  /**
   * Simulate contestants that only switch 
   *
   * @param trials Number of trials
   * @return A record called 'Result' which contains the amount of wins and losses
   */
  public static Result simulateSwitchContestants(int trials) {
    int wins = 0;
    int losses = 0;

    for (int i=0; i < trials; i++) {
      int winning_door = randInt(0, 2);
      int contestant_pick = randInt(0, 2);

      if (contestant_pick != winning_door) {
        wins++;
        continue;
      }

      losses++;
    }


    return new Result(wins, losses);
  }

  /**
   * Simulate contestants that only stay
   * 
   * @param trials Number of trials
   * @return A record called 'Result' which contains the amount of wins and losses
   */
  public static Result simulateStayContestants(int trials) {
    int wins = 0;
    int losses = 0;

    for (int i=0; i < trials; i++) {
      int winning_door = randInt(0, 2);
      int contestant_pick = randInt(0, 2);

      if (winning_door == contestant_pick) {
        wins++;
        continue;
      }

      losses++;
    }

    return new Result(wins, losses);
  }

  public static void main(String[] args) {
    if (args.length <= 0) {
      System.out.println("You gotta tell me the number of trials. Hint: Put the number of trials you want as an argument.");
      return;
    }

    /* Total number of trials to run between the two modes of simulation */
    int number_of_trials = 0;
    try {
      number_of_trials = Integer.parseInt(args[0]) / 2;

      if (number_of_trials <= 0) {
        System.out.println("The number of trials must be above 0.");
        return;
      }
    } catch (NumberFormatException e) {
      System.out.println("Please give a proper number that only consists of 0-9 with no punctuation or other symbols.");
      return;
    }

    /* We run two modes of simulation: always switch and always stay */
    Result result_switch = simulateSwitchContestants(number_of_trials);
    Result result_stay = simulateStayContestants(number_of_trials);

    ResultAggregator aggregator = new ResultAggregator(result_switch, result_stay);

    aggregator.createTwoWayTableCSV("monty_hall_two_way_table");
  }
}
