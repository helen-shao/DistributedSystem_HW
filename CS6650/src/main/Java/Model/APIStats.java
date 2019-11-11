/*
 * Ski Data API for NEU Seattle distributed systems course
 * An API for an emulation of skier managment system for RFID tagged lift tickets. Basis for CS6650 Assignments for 2019
 *
 * OpenAPI spec version: 1.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package Model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * APIStats
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-11-01T23:12:34.703Z[GMT]")
public class APIStats {
  @SerializedName("endpointStats")
  private List<APIStatsEndpointStats> endpointStats = null;

  public APIStats endpointStats(List<APIStatsEndpointStats> endpointStats) {
    this.endpointStats = endpointStats;
    return this;
  }

  public APIStats addEndpointStatsItem(APIStatsEndpointStats endpointStatsItem) {
    if (this.endpointStats == null) {
      this.endpointStats = new ArrayList<APIStatsEndpointStats>();
    }
    this.endpointStats.add(endpointStatsItem);
    return this;
  }

   /**
   * Get endpointStats
   * @return endpointStats
  **/
  @Schema(description = "")
  public List<APIStatsEndpointStats> getEndpointStats() {
    return endpointStats;
  }

  public void setEndpointStats(List<APIStatsEndpointStats> endpointStats) {
    this.endpointStats = endpointStats;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    APIStats apIStats = (APIStats) o;
    return Objects.equals(this.endpointStats, apIStats.endpointStats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endpointStats);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class APIStats {\n");

    sb.append("    endpointStats: ").append(toIndentedString(endpointStats)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
