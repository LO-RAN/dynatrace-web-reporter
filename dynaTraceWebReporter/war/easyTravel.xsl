<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet  [
	<!ENTITY nbsp   "&#160;">
]>
<!-- XSL HTML document -->
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:include href="lib/BarChart.xsl" />
	<xsl:include href="lib/TrafficLightChart.xsl" />
	<xsl:output method="html" encoding="UTF-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<xsl:template match="/">
		<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
				<title>Operations Console</title>
				<style type="text/css">
					body { background-color: #000; }
					body,td,th { color: #FFF; }
				</style>
			</head>

			<body>
				<table width="100%" border="0">
					<tr>
						<td height="120px" colspan="2" style="background-image: url(img/easyTravelBanner.png); background-repeat: no-repeat;" />
					</tr>
					<tr>
						<td height="15px" colspan="2"></td>
					</tr>
					<tr>
						<td height="60px" colspan="2" style="background-image: url(img/easyTravelSectionLogins.png); background-repeat: no-repeat;" />
					</tr>
					<tr>
						<td width="380px" height="380px">
							<!-- draw login business transaction response time traffic light -->
							<xsl:call-template name="drawTrafficLight">
								<xsl:with-param name="data" select="//chartdashlet[@name='Login - Response Time']" />
								<xsl:with-param name="width" select="370" />
								<xsl:with-param name="height" select="370" />
								<xsl:with-param name="levelWarning" select="3000" />
								<xsl:with-param name="levelSevere" select="5000" />
							</xsl:call-template>							
						</td>
						<td>
							<!-- draw login business transaction response times bar chart -->
							<xsl:call-template name="drawBarChart">
								<xsl:with-param name="data" select="//chartdashlet[@name='Login - Response Time']" />
								<xsl:with-param name="aggregation" select="'Average'" />
								<xsl:with-param name="width" select="600" />
								<xsl:with-param name="height" select="370" />
								<xsl:with-param name="marginLeft" select="100" />
								<xsl:with-param name="marginBottom" select="50" />
								<xsl:with-param name="marginAxisText" select="10" />
								<xsl:with-param name="yAxisUnit" select="'ms'" />
								<xsl:with-param name="fontSize" select="20" />
								<xsl:with-param name="colorAxisLine" select="'gray'" />
								<xsl:with-param name="colorAxisText" select="'gray'" />
								<xsl:with-param name="colorHorizontalMarkerLine" select="'gray'" />
								<xsl:with-param name="colorBarFill" select="'orange'" />
								<xsl:with-param name="colorBarStroke" select="'gray'" />
							</xsl:call-template>
						</td>
					</tr>
					<tr>
						<td height="15px" colspan="2"></td>
					</tr>
					<tr>
						<td height="60px" colspan="2" style="background-image: url(./img/easyTravelSectionTripSearch.png); background-repeat: no-repeat;" />
					</tr>
					<tr>
						<td width="380px" height="380px">
							<!-- draw trip search business transaction response times bar chart -->
							<xsl:call-template name="drawTrafficLight">
								<xsl:with-param name="data" select="//chartdashlet[@name='Search - Response Time']" />
								<xsl:with-param name="width" select="370" />
								<xsl:with-param name="height" select="370" />
								<xsl:with-param name="levelWarning" select="3000" />
								<xsl:with-param name="levelSevere" select="5000" />
							</xsl:call-template>
						</td>
						<td>
							<!-- draw trip search business transaction response times bar chart -->
							<xsl:call-template name="drawBarChart">
								<xsl:with-param name="data" select="//chartdashlet[@name='Search - Response Time']" />
								<xsl:with-param name="aggregation" select="'Average'" />
								<xsl:with-param name="width" select="600" />
								<xsl:with-param name="height" select="370" />
								<xsl:with-param name="marginLeft" select="100" />
								<xsl:with-param name="marginBottom" select="50" />
								<xsl:with-param name="marginAxisText" select="10" />
								<xsl:with-param name="yAxisUnit" select="'ms'" />
								<xsl:with-param name="fontSize" select="20" />
								<xsl:with-param name="colorAxisLine" select="'gray'" />
								<xsl:with-param name="colorAxisText" select="'gray'" />
								<xsl:with-param name="colorHorizontalMarkerLine" select="'gray'" />
								<xsl:with-param name="colorBarFill" select="'orange'" />
								<xsl:with-param name="colorBarStroke" select="'gray'" />
							</xsl:call-template>						
						</td>
					</tr>
					<td height="15px" colspan="2"></td>

					<tr>
						<td height="60px" colspan="2" style="background-image: url(./img/easyTravelSectionBooking.png); background-repeat: no-repeat;" />
					</tr>
					<tr>
						<td width="380px" height="380px">
							<!-- draw trip search business transaction response times bar chart -->
							<xsl:call-template name="drawTrafficLight">
								<xsl:with-param name="data" select="//chartdashlet[@name='Booking - Response Time']" />
								<xsl:with-param name="width" select="370" />
								<xsl:with-param name="height" select="370" />
								<xsl:with-param name="levelWarning" select="3000" />
								<xsl:with-param name="levelSevere" select="5000" />
							</xsl:call-template>
						</td>
						<td>
							<!-- draw booking business transaction response times bar chart -->
							<xsl:call-template name="drawBarChart">
								<xsl:with-param name="data" select="//chartdashlet[@name='Booking - Response Time']" />
								<xsl:with-param name="aggregation" select="'Average'" />
								<xsl:with-param name="width" select="600" />
								<xsl:with-param name="height" select="370" />
								<xsl:with-param name="marginLeft" select="100" />
								<xsl:with-param name="marginBottom" select="50" />
								<xsl:with-param name="marginAxisText" select="10" />
								<xsl:with-param name="yAxisUnit" select="'ms'" />
								<xsl:with-param name="fontSize" select="20" />
								<xsl:with-param name="colorAxisLine" select="'gray'" />
								<xsl:with-param name="colorAxisText" select="'gray'" />
								<xsl:with-param name="colorHorizontalMarkerLine" select="'gray'" />
								<xsl:with-param name="colorBarFill" select="'orange'" />
								<xsl:with-param name="colorBarStroke" select="'gray'" />
							</xsl:call-template>							
						</td>
					</tr>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>