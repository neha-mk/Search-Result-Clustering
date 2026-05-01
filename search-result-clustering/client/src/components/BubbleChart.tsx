import React, { useEffect, useState } from "react";
import Highcharts from "highcharts";
import HC_more from "highcharts/highcharts-more";
import HighchartsReact from "highcharts-react-official";
import convertedDataPackedBubbleFormat from "./BubbleChartHelper";
import { useData } from "./DataContext";
HC_more(Highcharts);


const BubbleChart = () => {
  const { fetchedData } = useData();

  const [bubblechartOptions, setBubblechartOptions] =
    useState<Highcharts.Options>({
      chart: {
        type: "packedbubble",
      },
      plotOptions: {
        packedbubble: {
          layoutAlgorithm: {
            gravitationalConstant: 0.25,
            splitSeries: true,
            parentNodeLimit: true,
          },
        },
      },
      colorAxis: {
        min: 0,
        stops: [
          [0.4, '#ffff00'],
          [0.65, '#bfff00'],
          [1, '#40ff00'],
        ],
      },
      tooltip: {
        useHTML: true,
        pointFormat: `<b>Title:</b> {point.name}<br>
                      <b>Relevance score:</b> {point.value}<br>
                      <b>Content:</b> {point.content}`,
      },
      title: undefined,
      series: undefined,
    });

  useEffect(() => {
    if (fetchedData) {
      const graphSeriesData: any = convertedDataPackedBubbleFormat(fetchedData);
      
      graphSeriesData.forEach((series: any) => {
        series.data.forEach((point: any, index: number) => {
          if (point.value >= 0.75) {
            point.color = '#ff0000';
          } else if (point.value >= 0.5) {
            point.color = '#000'; 
          } else {
            point.color = '#fff';
          }
        });
      });

      setBubblechartOptions({ series: graphSeriesData });
    }
  }, [fetchedData]);

  return (
    <div>
      <HighchartsReact highcharts={Highcharts} options={bubblechartOptions} />
      Relevance score
      <ul className="legend">
        <li><span className="superawesome"></span>More than or equal to 0.75</li>
        <li><span className="awesome"></span>More than or equal to 0.5 and less than 0.75</li>
        <li><span className="kindaawesome"></span>Less than 0.5</li>
      </ul>
    </div>
  );
};

export default BubbleChart;
