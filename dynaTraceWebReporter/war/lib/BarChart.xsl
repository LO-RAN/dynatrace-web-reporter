<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet>

<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Includes -->
  <xsl:include href="DateUtil.xsl"/>
  
  <!-- Constants -->
  <xsl:variable name="FONT" select="'Arial'" />
   
  <!-- Helper function to return the minimum time stamp from the given measures -->
  <xsl:template name="minimumTimeStamp">
    <xsl:param name="data" />
    <xsl:for-each select="$data">
      <xsl:sort select="@timestamp" data-type="number" order="ascending" />
      <xsl:if test="position()=1">
        <xsl:value-of select="@timestamp" />
      </xsl:if>
    </xsl:for-each>
  </xsl:template>

  <!-- Helper function to return the maximum time stamp from the given measures -->
  <xsl:template name="maximumTimeStamp">
    <xsl:param name="data" />
    <xsl:for-each select="$data">
      <xsl:sort select="@timestamp" data-type="number" order="descending" />
      <xsl:if test="position()=1">
        <xsl:value-of select="@timestamp" />
      </xsl:if>
    </xsl:for-each>
  </xsl:template>
  
  <!-- Helper function to return the maximum value from given measures -->
  <xsl:template name="getMaximumMeasureValue">
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
 
  <!-- Helper method to draw the bar chart x axis -->
  <xsl:template name="drawBarChartXAxisBar">
    <xsl:param name="data"/>
    <xsl:param name="referencedata"/>
    <xsl:param name="marginLeft" select="10" />
    <xsl:param name="marginRight" select="0" />
    <xsl:param name="marginTop" select="0" />
    <xsl:param name="marginBottom" select="10" />
    <xsl:param name="width" select="'100%'" />
    <xsl:param name="height" select="'100%'" />
    <xsl:param name="fontSize" select="5" />
    <xsl:param name="yAxisMaxValue"/>
    <xsl:param name="from"/>
    <xsl:param name="to"/>
    <xsl:param name="interval"/>
    <xsl:param name="current" select="$from"/>
    <xsl:param name="index" select="0"/>
    <xsl:param name="colorBarFill" select="'black'" />
    <xsl:param name="colorBarStroke" select="'black'" />
    <!-- helper variables-->
    <xsl:variable name="chartCanvasHeight" select="($height)-($marginBottom)-($marginTop)"/>
    <xsl:variable name="chartCanvasWidth" select="($width)-($marginLeft)-($marginRight)"/>
    <xsl:variable name="barCount" select="(($to - $from) div $interval) + 1" />
    <xsl:variable name="barWidth" select="$chartCanvasWidth div ($barCount)" />
    <xsl:variable name="measureValue" select="$data/measures[1]/measure/measurement[@timestamp=$current]/@avg"/>
    <xsl:variable name="barHeight" select="($measureValue * $chartCanvasHeight) div $yAxisMaxValue" />
    
    <!-- draw vertical measure value bar -->
    <rect x="{$marginLeft + ($index * $barWidth)}" y="{$marginTop + ($chartCanvasHeight -$barHeight)}" rx="0" ry="0" width="{$barWidth}" height="{$barHeight}" fill="{$colorBarFill}" stroke="{$colorBarStroke}" stroke-width="1" />
    
    <!-- continue Loop -->
    <xsl:variable name="next" select="$current + $interval" />
    <xsl:variable name="nextIndex" select="$index + 1" />
    <xsl:if test="($to + 1) > $next">
      <xsl:call-template name="drawBarChartXAxisBar">
        <xsl:with-param name="data" select="$data" />
        <xsl:with-param name="referencedata" select="$referencedata" />
        <xsl:with-param name="marginLeft" select="$marginLeft" />
        <xsl:with-param name="marginRight" select="$marginRight" />
        <xsl:with-param name="marginTop" select="$marginTop" />
        <xsl:with-param name="marginBottom" select="$marginBottom" />
        <xsl:with-param name="width" select="$width" />
        <xsl:with-param name="height" select="$height" />
        <xsl:with-param name="fontSize" select="$fontSize" />
        <xsl:with-param name="yAxisMaxValue" select="$yAxisMaxValue" />
        <xsl:with-param name="from" select="$from" />
        <xsl:with-param name="to" select="$to" />
        <xsl:with-param name="interval" select="$interval" />
        <xsl:with-param name="current" select="$next" />
        <xsl:with-param name="index" select="$nextIndex" />
        <xsl:with-param name="colorBarFill" select="$colorBarFill" />
        <xsl:with-param name="colorBarStroke" select="$colorBarStroke" />
      </xsl:call-template>
    </xsl:if>     
  </xsl:template>
  
  <!-- Helper method to draw the bar chart y axis -->
  <xsl:template name="drawBarChartXAxisLabel">
    <xsl:param name="data"/>
    <xsl:param name="marginLeft" select="10" />
    <xsl:param name="marginRight" select="0" />
    <xsl:param name="marginTop" select="0" />
    <xsl:param name="marginBottom" select="10" />
    <xsl:param name="marginAxisText" select="5" />
    <xsl:param name="xAxisStepCount" select="10" />
    <xsl:param name="xAxisMinValue" />
    <xsl:param name="xAxisMaxValue" />
    <xsl:param name="width" select="'100%'" />
    <xsl:param name="height" select="'100%'" />
    <xsl:param name="xAxisStepIndex" select="0" />
    <xsl:param name="fontSize" select="5" />
    <xsl:param name="colorAxisText" select="'black'" /> 
    <xsl:if test="$xAxisStepIndex &lt; ($xAxisStepCount +1)">
      <!-- helper variables -->
      <xsl:variable name="chartCanvasHeight" select="($height)-($marginBottom)-($marginTop)"/>
      <xsl:variable name="chartCanvasWidth" select="($width)-($marginLeft)-($marginRight)"/>
      <xsl:variable name="xAxisStepSize" select="floor(($chartCanvasWidth) div ($xAxisStepCount))"/>
      
      <!-- x axis label text -->
      <xsl:variable name="xAxisStepValue" select="floor(((($xAxisMaxValue - $xAxisMinValue) div $xAxisStepCount) * $xAxisStepIndex) + $xAxisMinValue)"/>
      <text writing-mode="tb" x="{(($marginLeft + ($xAxisStepIndex * $xAxisStepSize)) - ($fontSize div 2) + 1)}" dy="{$marginTop + $chartCanvasHeight + $marginAxisText}" text-anchor="start" fill="{$colorAxisText}" font-family="{$FONT}" font-weight="bold" font-size="{$fontSize}" xmlns:svg="http://www.w3.org/2000/svg">
        <!-- DEBUG <xsl:value-of select="round($xAxisStepValue)" />-->
        <xsl:call-template name="getHoursAndMinutes">
          <xsl:with-param name="date" select="round($xAxisStepValue)" />
        </xsl:call-template>
      </text>

      <!-- continue loop -->
      <xsl:call-template name="drawBarChartXAxisLabel">
        <xsl:with-param name="marginLeft" select="$marginLeft" />
        <xsl:with-param name="marginRight" select="$marginRight" />
        <xsl:with-param name="marginTop" select="$marginTop" />
        <xsl:with-param name="marginBottom" select="$marginBottom" />
        <xsl:with-param name="marginAxisText" select="$marginAxisText" />
        <xsl:with-param name="xAxisStepCount" select="$xAxisStepCount" />
        <xsl:with-param name="xAxisMinValue" select="$xAxisMinValue" />
        <xsl:with-param name="xAxisMaxValue" select="$xAxisMaxValue" />
        <xsl:with-param name="width" select="$width" />
        <xsl:with-param name="height" select="$height" />
        <xsl:with-param name="xAxisStepIndex" select="$xAxisStepIndex + 1" />
        <xsl:with-param name="fontSize" select="$fontSize" />
        <xsl:with-param name="colorAxisText" select="$colorAxisText" />
      </xsl:call-template>
    </xsl:if>
  </xsl:template>

  <!-- Helper method to draw the bar chart y axis -->
  <xsl:template name="drawBarChartYAxis">
    <xsl:param name="data"/>
    <xsl:param name="marginLeft" select="10" />
    <xsl:param name="marginRight" select="0" />
    <xsl:param name="marginTop" select="0" />
    <xsl:param name="marginBottom" select="10" />
    <xsl:param name="marginAxisText" select="5" />
    <xsl:param name="yAxisStepCount" select="10" />
    <xsl:param name="yAxisMaxValue" />
    <xsl:param name="yAxisUnit" />
    <xsl:param name="width" select="'100%'" />
    <xsl:param name="height" select="'100%'" />
    <xsl:param name="yAxisStepIndex" select="0" />
    <xsl:param name="fontSize" select="5" />
    <xsl:param name="colorAxisText" select="'black'" /> 
    <xsl:param name="colorHorizontalMarkerLine" select="'black'" /> 
    <xsl:if test="$yAxisStepIndex &lt; ($yAxisStepCount +1)">
      <!-- helper variables -->
      <xsl:variable name="chartCanvasHeight" select="($height)-($marginBottom)-($marginTop)"/>
      <xsl:variable name="yAxisStepSize" select="floor(($chartCanvasHeight) div ($yAxisStepCount))"/>
      
      <!-- y axis horizontal line -->
      <xsl:if test="$yAxisStepIndex &gt; 0">
        <line x1="{$marginLeft}" y1="{($height)-($marginBottom)-($yAxisStepIndex*$yAxisStepSize)}" x2="{($width)-($marginRight)}" y2="{($height)-($marginBottom)-($yAxisStepIndex*$yAxisStepSize)}" stroke="{$colorHorizontalMarkerLine}" stroke-width="1" />
      </xsl:if> 

      <!-- y axis label text -->
      <xsl:variable name="yAxisStepValue" select="($yAxisMaxValue div $yAxisStepCount) * $yAxisStepIndex"/>
      <text x="{($marginLeft)-($marginAxisText)}" y="{($height)-($marginBottom)-($yAxisStepIndex*$yAxisStepSize)+($fontSize div 2)-2}" text-anchor="end" fill="{$colorAxisText}" font-family="{$FONT}" font-weight="bold" font-size="{$fontSize}" xmlns:svg="http://www.w3.org/2000/svg">
        <xsl:value-of select="round($yAxisStepValue)" /><xsl:value-of select="' '" /><xsl:value-of select="$yAxisUnit" />
      </text>

      <!-- continue loop -->
      <xsl:call-template name="drawBarChartYAxis">
        <xsl:with-param name="marginLeft" select="$marginLeft" />
        <xsl:with-param name="marginRight" select="$marginRight" />
        <xsl:with-param name="marginTop" select="$marginTop" />
        <xsl:with-param name="marginBottom" select="$marginBottom" />
        <xsl:with-param name="marginAxisText" select="$marginAxisText" />
        <xsl:with-param name="yAxisStepCount" select="$yAxisStepCount" />
        <xsl:with-param name="yAxisMaxValue" select="$yAxisMaxValue" />
        <xsl:with-param name="yAxisUnit" select="$yAxisUnit" />
        <xsl:with-param name="width" select="$width" />
        <xsl:with-param name="height" select="$height" />
        <xsl:with-param name="yAxisStepIndex" select="$yAxisStepIndex + 1" />
        <xsl:with-param name="fontSize" select="$fontSize" />
        <xsl:with-param name="colorAxisText" select="$colorAxisText" />
        <xsl:with-param name="colorHorizontalMarkerLine" select="$colorHorizontalMarkerLine" />
      </xsl:call-template>
    </xsl:if>
  </xsl:template>
  
  <!-- Main method to draw a bar chart -->
  <xsl:template name="drawBarChart">
    <xsl:param name="data"/>
    <xsl:param name="referencedata" select="$data"/>
    <xsl:param name="marginLeft" select="30" />
    <xsl:param name="marginRight" select="2" />
    <xsl:param name="marginTop" select="10" />
    <xsl:param name="marginBottom" select="40" />
    <xsl:param name="marginAxisText" select="5" />
    <xsl:param name="xAxisStepCount" select="5" />
    <xsl:param name="yAxisStepCount" select="5" />
    <xsl:param name="yAxisUnit" select="''" />
    <xsl:param name="timeInterval" select="30000" />
    <xsl:param name="width" select="'100%'" />
    <xsl:param name="height" select="'100%'" />
    <xsl:param name="fontSize" select="10" />
    <xsl:param name="colorAxisLine" select="'black'" />
    <xsl:param name="colorAxisText" select="'black'" />
    <xsl:param name="colorHorizontalMarkerLine" select="'black'" />
    <xsl:param name="colorBarFill" select="'black'" />
    <xsl:param name="colorBarStroke" select="'black'" />
    <svg version="1.1" width="{$width}" height="{$height}" preserveAspectRatio="xMinYMin" xmlns="http://www.w3.org/2000/svg">
      <xsl:variable name="fromTime">
        <xsl:call-template name="minimumTimeStamp">
          <xsl:with-param name="data" select="$referencedata/measures[1]/measure/measurement" />
        </xsl:call-template>
      </xsl:variable>    
      <xsl:variable name="toTime">
        <xsl:call-template name="maximumTimeStamp">
          <xsl:with-param name="data" select="$referencedata/measures[1]/measure/measurement" />
        </xsl:call-template>
      </xsl:variable> 
      <xsl:variable name="yAxisMaxValue">
        <xsl:call-template name="getMaximumMeasureValue">
          <xsl:with-param name="measures" select="$data/measures[1]/measure/measurement" />
        </xsl:call-template>
      </xsl:variable>
      
      <!-- draw y axis (labels and horizontal lines)-->
      <xsl:call-template name="drawBarChartYAxis">
        <xsl:with-param name="marginLeft" select="$marginLeft" />
        <xsl:with-param name="marginRight" select="$marginRight" />
        <xsl:with-param name="marginTop" select="$marginTop" />
        <xsl:with-param name="marginBottom" select="$marginBottom" />
        <xsl:with-param name="yAxisStepCount" select="$yAxisStepCount" />
        <xsl:with-param name="yAxisMaxValue" select="$yAxisMaxValue" />
        <xsl:with-param name="yAxisUnit" select="$yAxisUnit" />
        <xsl:with-param name="width" select="$width" />
        <xsl:with-param name="height" select="$height" />
        <xsl:with-param name="fontSize" select="$fontSize" />
        <xsl:with-param name="marginAxisText" select="$marginAxisText" />
        <xsl:with-param name="colorAxisText" select="$colorAxisText" />
        <xsl:with-param name="colorHorizontalMarkerLine" select="$colorHorizontalMarkerLine" />
      </xsl:call-template>
      
      <!-- draw x axis (vertical bars)-->
      <xsl:call-template name="drawBarChartXAxisBar">
        <xsl:with-param name="data" select="$data" />
        <xsl:with-param name="referencedata" select="$referencedata" />
        <xsl:with-param name="marginLeft" select="$marginLeft" />
        <xsl:with-param name="marginRight" select="$marginRight" />
        <xsl:with-param name="marginTop" select="$marginTop" />
        <xsl:with-param name="marginBottom" select="$marginBottom" />
        <xsl:with-param name="width" select="$width" />
        <xsl:with-param name="height" select="$height" />
        <xsl:with-param name="fontSize" select="$fontSize" />
        <xsl:with-param name="yAxisMaxValue" select="$yAxisMaxValue" />
        <xsl:with-param name="from" select="$fromTime" />
        <xsl:with-param name="to" select="$toTime" />
        <xsl:with-param name="interval" select="$timeInterval" />
        <xsl:with-param name="colorBarFill" select="$colorBarFill" />
        <xsl:with-param name="colorBarStroke" select="$colorBarStroke" />
      </xsl:call-template>
      
      <!-- draw x axis (vertical labels)-->
      <xsl:call-template name="drawBarChartXAxisLabel">
        <xsl:with-param name="data"/>
        <xsl:with-param name="marginLeft" select="$marginLeft" />
        <xsl:with-param name="marginRight" select="$marginRight" />
        <xsl:with-param name="marginTop" select="$marginTop" />
        <xsl:with-param name="marginBottom" select="$marginBottom" />
        <xsl:with-param name="marginAxisText" select="$marginAxisText" />
        <xsl:with-param name="xAxisStepCount" select="$xAxisStepCount" />
        <xsl:with-param name="xAxisMinValue" select="$fromTime"/>
        <xsl:with-param name="xAxisMaxValue" select="$toTime"/>
        <xsl:with-param name="width" select="$width" />
        <xsl:with-param name="height" select="$height" />
        <xsl:with-param name="fontSize" select="$fontSize" />
        <xsl:with-param name="colorAxisText" select="$colorAxisText" />
      </xsl:call-template>
      
      <!-- draw x axis -->
      <line x1="{$marginLeft}" y1="{($height)-($marginBottom)}" x2="{($width)-($marginRight)}" y2="{($height)-($marginBottom)}" stroke="{$colorAxisLine}" stroke-width="2" />
      
      <!-- draw y axis -->
      <line x1="{$marginLeft}" y1="{$marginTop}" x2="{$marginLeft}" y2="{($height)-($marginBottom)}" stroke="{$colorAxisLine}" stroke-width="2" />
      
    </svg> 
  </xsl:template>    

</xsl:stylesheet>