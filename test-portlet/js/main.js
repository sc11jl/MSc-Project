var dataFilename = "http://www.paultownend.com/junyi/testdata.csv";
drawgraph(dataFilename);

function drawgraph(url){
d3.csv(url, type, function(data){

    //console.log(data);
    var width = 500,
    height = 1000,
    margin = {left:50, top:30, right:50, bottom:30},
    svg_height = height+margin.top+margin.bottom,
    svg_width = width+margin.left+margin.right;


var scale_utilisation = d3.scale.linear()
    .domain([0,100])
    .range([0,width]);

var scale_power = d3.scale.linear()
    .domain([0,d3.max(data,function(d){return d.Power_Consumption;
    })])
    .range([0,width]);

var scale_y = d3.scale.ordinal()
    .domain(data.map(function(d){return d.Node;}))
    .rangeBands([0,height],0.2);

var tip_utilisation = d3.tip()
    .attr("class","d3-tip")
    .offset([-10,0])
    .html(function(d){
        return "<strong>Resource Utilisation:</strong> <span style='color:blue'>" + d.Utilisation+" %" + "</span>";
    });

var tip_power = d3.tip()
    .attr("class","d3-tip")
    .offset([-10,0])
    .html(function(d){
        return "<strong>Power Consumption:</strong> <span style='color:blue'>" + d.Power_Consumption+" KwH" + "</span>";
    });

var svg = d3.select("#container")
    .append("svg")
    .attr("width",svg_width)
    .attr("height",svg_height);

var chart = svg.append("g")
    .attr("transform","translate(" + margin.left + "," + margin.top + ")");

chart.call(tip_power);
chart.call(tip_utilisation);

var y_axis = d3.svg.axis().scale(scale_y).orient("left");

chart.append("g")
    .call(y_axis)
    .append("text")
    .text("Node")
    .attr({
        "text-anchor":"left",
        "dx":"-2em",
        "font-weight":"600"
    });

var bar = chart.selectAll(".bar")
    .data(data)
    .enter()
    .append("g")
    .attr("class",".bar")
    .attr("transform", function(d,i){
        return "translate(0," + scale_y(d.Node) +")";
    });

var avail = bar.append("rect")
    .attr({
        "width":width,
        "height":scale_y.rangeBand()/2
    })
    .style("fill","#33FF00");
    
var utilisation = bar.append("rect")
    .attr({
        "width":function(d){return scale_utilisation(d.Utilisation);},
        "height":scale_y.rangeBand()/2
    })
    .style("fill","#FF0000")
    .on("mouseover",tip_utilisation.show)
    .on("mouseout",tip_utilisation.hide);

    
var powerConsumption = bar.append("rect")
    .attr("transform","translate(0,"+scale_y.rangeBand()/2+")")
    .attr({
        "width":function(d){return scale_power(d.Power_Consumption);},
        "height":scale_y.rangeBand()/2
    })
    .style("fill","yellow")
    .on("mouseover",tip_power.show)
    .on("mouseout",tip_power.hide);


/*
bar.append("text")
    .text(function(d){return d3.format(".2f")(d.Utilisation)+"%";})
    .attr({
        "x":width,
        "y":(scale_y.rangeBand()+0.2)/2
    });
*/


});

function type(d) {
    d.Utilisation =+ d.Utilisation;
    d.Power_Consumption =+ d.Power_Consumption;
    return d;
}
}