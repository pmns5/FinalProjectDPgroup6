PLOT_LENGTH = 50;

function constructFilmView(obj) {
  let cutPlot;
  if (obj.plot.length > PLOT_LENGTH) {
    cutPlot = obj.plot.substr(0, PLOT_LENGTH) + "...";
  } else {
    cutPlot = obj.plot;
  }
  return (
    '<div class="col-sm-4">' +
    '   <div class="our_2">' +
    '<div class="ih-item square effect5 left_to_right" > ' +
    '<a href="../../html/client/FeedbackFilm.html?id=' +
    obj.id +
    '">' +
    "           <img src=data:image/jpeg;base64," +
    obj.poster +
    ' alt="img" >' +
    '            <div class="info">' +
    "               <h3>" +
    obj.title +
    "</h3>" +
    '               <p style="font-size: small">' +
    cutPlot +
    "</p>" +
    addActors(obj.actors) +
    addStars(obj.avgScore) +
    "            </div>" +
    "       </a></div>" +
    "   </div>" +
    "</div>"
  );
}

function addStars(avg) {
  
  return (
    '<div class="rating">' +
    '       <div class="rating-upper" style="width: ' +
    avg * 20  +
    '%">' +
    "           <span>★</span>" +
    "           <span>★</span>" +
    "           <span>★</span>             " +
    "           <span>★</span>             " +
    "           <span>★</span>             " +
    "        </div>                         " +
    '        <div class="rating-lower"> ' +
    "             <span>★</span>             " +
    "             <span>★</span>             " +
    "             <span>★</span>             " +
    "             <span>★</span>             " +
    "             <span>★</span>             " +
    "         </div>                         " +
    "     </div>                             "
  );
}

function addActors(actorList) {
  let actors = "";
  $.each(actorList, function (index, obj) {
    actors += " " + obj.name + " " + obj.surname;
    if (actors.length > PLOT_LENGTH) {
      actors += "... ";
      return false;
    } else {
      actors += ",";
    }
  });
  actors = actors.substring(0, actors.length - 1);
  return '<h6 class="filmText" style="color: white">' + actors + "</h6>";
}
