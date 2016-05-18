import d3 from 'd3';
import _ from 'lodash';

const margin = { top: 20, right: 20, bottom: 30, left: 40 };
const width = 960 - margin.left - margin.right;
const height = 500 - margin.top - margin.bottom;

class ScatterPlot {
  constructor() {
    this.data = [];
    this.init();
  }

  init() {
    // setup svg
    this.svg = d3.select('body').append('svg')
      .attr('width', width + margin.left + margin.right)
      .attr('height', height + margin.top + margin.bottom)
      .append('g')
        .attr('transform', `translate(${margin.left}, ${margin.top})`);

    // range and scales
    this.x = d3.scale.linear()
      .range([0, width]);
    this.y = d3.scale.linear()
      .range([height, 0]);
    this.xAxis = d3.svg.axis()
      .scale(this.x)
      .orient('bottom');
    this.yAxis = d3.svg.axis()
      .scale(this.y)
      .orient('left');
  }

  addData(data) {
    // convert data values to integers instead of strings
    data = _.map(data, d => _.mapValues(d, parseInt));

    this.x.domain(d3.extent(data, d => d.PositionX)).nice();
    this.y.domain(d3.extent(data, d => d.PositionY)).nice();

    this.draw(data);
  }

  draw(data) {
    this.svg.selectAll(".dot")
      .data(data)
    .enter().append("circle")
      .attr("class", "dot")
      .attr("r", 3.5)
      .attr("cx", d => this.x(d.PositionX))
      .attr("cy", d => this.y(d.PositionY));
  }
}

export default ScatterPlot;
