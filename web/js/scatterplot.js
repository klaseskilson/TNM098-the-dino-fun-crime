import d3 from 'd3';
import _ from 'lodash';

const margin = { top: 0, right: 0, bottom: 80, left: 0 };
const width = 700 - margin.left - margin.right;
const height = 700 - margin.top - margin.bottom;

const opacityRange = 0.99;
const opacityOffset = 1 - opacityRange;

class ScatterPlot {
  constructor() {
    this.max = 0;
    this.init();
  }

  init() {
    // setup svg
    this.svg = d3.select('#sp').append('svg')
      .attr('width', width + margin.left + margin.right)
      .attr('height', height + margin.top + margin.bottom)
      .append('g')
        .attr('transform', `translate(${margin.left}, ${margin.top})`);

    // setup tooltip
    this.tooltip = d3.select('body').append('span');

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

    this.max = _.maxBy(data, 'Amount').Amount;

    this.x.domain(d3.extent(data, d => d.PositionX)).nice();
    this.y.domain(d3.extent(data, d => d.PositionY)).nice();

    this.draw(data);

    var sliderData = d3.select("#slider").on("input", (value) => {
      document.getElementById("slider-value").innerHTML = Math.round(d3.select("#slider")[0][0].value*100);
      var filteredData = _.filter(data, d => d.Group == Math.round(d3.select("#slider")[0][0].value*100));
      this.max = _.maxBy(filteredData, 'Amount').Amount;
      this.x.domain(d3.extent(filteredData, d => d.PositionX)).nice();
      this.y.domain(d3.extent(filteredData, d => d.PositionY)).nice();
      this.draw(filteredData)        
    });
  }

  draw(data) {
    this.svg.selectAll(".dot").remove();
    this.svg.selectAll(".dot")
      .data(data)
    .enter().append("circle")
      .attr("class", "dot")
      .attr("r", 3.5)
      .attr("cx", d => this.x(d.PositionX))
      .attr("cy", d => this.y(d.PositionY))
      .on('mouseenter', d => {
        this.tooltip.html(JSON.stringify(d));
      })
      .attr('fill-opacity',
        d => opacityOffset + opacityRange * d.Amount / this.max)
      .style("fill", function(d){ return "Red";});

  }
}

export default ScatterPlot;
