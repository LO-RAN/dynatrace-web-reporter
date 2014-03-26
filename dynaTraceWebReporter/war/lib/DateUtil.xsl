<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet>

<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <!-- function to return time (HH:mm format) from a numeric (java, long) date -->
  <xsl:template name="getHoursAndMinutes">
    <xsl:param name="date" />
    <!-- For debugging with Oxygen -->
    <xsl:value-of select="$date" />
    
    <!-- For runtime with Tomcat -->
    <!--<xsl:value-of select="Date:getHourAndMinute($date)" xmlns:Date="java:org.loran.gwt.server.DateFormatUtil" />-->    
  </xsl:template>
  
</xsl:stylesheet>