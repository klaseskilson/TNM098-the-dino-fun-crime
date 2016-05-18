import d3 from 'd3';
import ScatterPlot from './scatterplot';

const sp = new ScatterPlot();

d3.csv('data/heatMap.csv', (error, data) => {
  if (error) console.error(error);

  sp.addData(data);
});
