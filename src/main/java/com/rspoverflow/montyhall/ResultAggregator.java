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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ResultAggregator {
  private Result switching_result;
  private Result staying_result;

  public ResultAggregator(Result switching_trials, Result staying_trials) {
    this.switching_result = switching_trials;
    this.staying_result = staying_trials;
  }

  /**
   * Outputs a CSV file content string that contains a two-way table.
   * The structure is:
   * Strategy,Wins,Losses
   * Switched,#W_switch,#L_switch
   * Stayed,#W_stay,#L_stay
   * 
   * @param file_name A file name that will have .csv appended on the back of it
   * 
   * @return A String containing the CSV data.
   */
  public void createTwoWayTableCSV(String file_name) {
    /* Use a StringBuilder for efficient string construction */
    StringBuilder csv = new StringBuilder();

    /** 
     * 1. Add Header Row
     * It should look like: Strategy,Wins,Losses 
     */
    csv.append("Strategy,Wins,Losses\n");

    /** 
     * 2. Add Switched Row
     * It should look like: Switching,#wins,#losses
     */
    csv.append("Switched,")
       .append(switching_result.wins())
       .append(",")
       .append(switching_result.losses())
       .append("\n");

    /** 
     * 3. Add Stayed Row
     * It should look like: Stayed,#wins,#losses
     */
    csv.append("Stayed,")
       .append(staying_result.wins())
       .append(",")
       .append(staying_result.losses())
       .append("\n");

    file_name += ".csv";

    try {
      Files.write(Paths.get(file_name), csv.toString().getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      System.out.println("monty: Aww, couldn't write the data file :(");
    }
  }
}
