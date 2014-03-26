<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet>

<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <!-- Helper function to return the maximum value from given measures -->
  <xsl:template name="getMaximumMeasureValueX">
    <xsl:param name="measures" />
    <xsl:param name="aggregation" select="Average"/>
    <xsl:choose>
      <xsl:when test="$aggregation='Minimum'">
        <xsl:for-each select="$measures">
          <xsl:sort select="@min" data-type="number" order="descending" />
          <xsl:if test="position()=1">
            <xsl:value-of select="@min" />
          </xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:when test="$aggregation='Maximum'">
        <xsl:for-each select="$measures">
          <xsl:sort select="@max" data-type="number" order="descending" />
          <xsl:if test="position()=1">
            <xsl:value-of select="@max" />
          </xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:when test="$aggregation='Count'">
        <xsl:for-each select="$measures">
          <xsl:sort select="@count" data-type="number" order="descending" />
          <xsl:if test="position()=1">
            <xsl:value-of select="@count" />
          </xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:when test="$aggregation='Sum'">
        <xsl:for-each select="$measures">
          <xsl:sort select="@sum" data-type="number" order="descending" />
          <xsl:if test="position()=1">
            <xsl:value-of select="@sum" />
          </xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:for-each select="$measures">
          <xsl:sort select="@avg" data-type="number" order="descending" />
          <xsl:if test="position()=1">
            <xsl:value-of select="@avg" />
          </xsl:if>
        </xsl:for-each>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template> 
  
  <!-- Prototype method for PageAction Dashlet -->
  <xsl:template name="PageActionDashletTable">
    <xsl:param name="data" />
    <!--table-layout:fixed; along with overflow:hidden; white-space: nowrap;-->
    <table id="yourTableID" width="100%" border="0" cellspacing="0" cellpadding="0" style="white-space:nowrap; table-layout: fixed;">
      <thead>
        <tr>
          <th width="15"></th>
          <th width="130">Time</th>
          <th>Page Action</th>
          <th width="50">Exec</th>
          <th width="200">Distribution</th>
        </tr>
      </thead>
      <tbody>
        <xsl:for-each select="$data/purepaths/purepath">
          <xsl:variable name="name" select="@name"/>
          <!-- <xsl:value-of select="$name" />-->
          <tr>
            <td align="center" style="overflow:hidden; text-overflow:ellipsis;">
              <xsl:choose>
                <xsl:when test="@state = 'Ok'">
                  <img src="img/purepathok.gif"/>
                </xsl:when>
                <xsl:otherwise>
                  <img src="img/purepathnotok.gif"/>
                </xsl:otherwise>
              </xsl:choose>
            </td>
            <td style="overflow:hidden; text-overflow:ellipsis;"><xsl:value-of select="@start" /></td>
            <td style="overflow:hidden; text-overflow:ellipsis;"><xsl:value-of select="@name" /></td>
            <td align="right" style="overflow:hidden; text-overflow:ellipsis;"><xsl:value-of select="@exec" /> ms</td>
            <td style="overflow:hidden; text-overflow:ellipsis;"><xsl:value-of select="@response_time" /></td>
          </tr>
        </xsl:for-each>
      </tbody>
    </table>
  </xsl:template> 
  
  <!-- Main method to draw a traffic light chart -->
  <xsl:template name="drawTrafficLight">
    <xsl:param name="data"/>
    <xsl:param name="width" select="100" />
    <xsl:param name="height" select="100" />
    <xsl:param name="levelWarning" select="0" />
    <xsl:param name="levelSevere" select="0" />
    <!-- helper variables -->
      <xsl:variable name="maximumMeasureValue">
        <xsl:call-template name="getMaximumMeasureValueX">
          <xsl:with-param name="measures" select="$data/measures[1]/measure/measurement" />
        </xsl:call-template>
      </xsl:variable>  
      
      <!-- No threshold violation  -->
      <xsl:if test="($maximumMeasureValue &lt; $levelWarning) and ($maximumMeasureValue &lt; $levelSevere)">
        <img src="./img/TrafficLightOkay.png" width="370" height="370" />
      </xsl:if>
        
      <!-- Threshold 'warning' violation  -->
      <xsl:if test="($maximumMeasureValue &gt; $levelWarning) or ($maximumMeasureValue = $levelWarning)">
        <xsl:if test="($maximumMeasureValue &lt; $levelSevere)">
          <img src="./img/TrafficLightWarning.png" width="370" height="370" />
        </xsl:if>
      </xsl:if>
        
      <!-- Threshold 'severe' violation  -->
      <xsl:if test="($maximumMeasureValue &gt; $levelSevere) or ($maximumMeasureValue = $levelSevere)">
        <img src="./img/TrafficLightSevere.png" width="370" height="370" />
      </xsl:if>
         
  </xsl:template> 
  
</xsl:stylesheet>