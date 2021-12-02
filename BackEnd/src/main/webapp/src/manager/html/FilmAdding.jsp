<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>Film Adding</title>
    <!-- Link to Bootstrap and JQuery Libraries -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="../css/myStyle.css">


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
</head>
<body>

<!-- Navbar Menu -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="index.html">Home</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link " href="FilmAdding.jsp">Film Adding</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="ActorsAdding.html">Actors Adding</a>
            </li>
        </ul>
        <div class="nav-item ml-auto">
            <form method="post">
                <input type="submit" value="Logout" class="btn btn-dark">
            </form>
        </div>

    </div>
</nav>

<header class="container">
    <h5>Film Adding</h5>
</header>

<!-- Banner Alert template and his section -->
<template id="success-alert-template">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <h4>{message}</h4>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</template>
<template id="fail-alert-template">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <h4>{message}</h4>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</template>
<div id="response-alert-section" class="container message"></div>

<!-- Table  -->
<section class="container">
    <div class="row">
        <table class="table table-bordered table-hover " id="table">
            <thead class="thead-light">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Plot</th>
                <th>Genre</th>
            </tr>
            </thead>
            <template id="table-template">
                <tr>
                    <td>{ID}</td>
                    <td>{Title}</td>
                    <td>{Plot}</td>
                    <td>{Genre}</td>
                    <td class="myIcon">
                        <button class="btn btn-outline-primary btn-sm" data-toggle="modal"
                                data-target="#edit-modal" onclick="controller.viewEdit({ID})">
                            <i class="fas fa-edit mr-2"></i>
                        </button>
                    </td>
                    <td class="myIcon"><button class="btn btn-outline-danger btn-sm"  data-toggle="modal"
                                               data-target="#delete-modal" onclick="controller.deleteView({ID})">
                        <i class="fas fa-trash mr-2"></i>
                    </button></td>
                </tr>
            </template>
            <tbody id="table-rows"></tbody>
            <tfoot></tfoot>
        </table>
    </div>
    <div class="row">
        <button id="insert-button" class="btn btn-outline-primary fixedbutton" data-toggle="modal"
                data-target="#insert-modal">
            Insert
        </button>
    </div>
</section>

<footer class="container">Copyright &copy Group 6, 2021</footer>

<!-- MODAL --------------------->

<!-- Popup per l'inserimento -->
<div class="modal fade demo-popup" id="insert-modal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Insert new Film</h5>
                <button type="button" class="close" aria-label="Close" data-dismiss="modal" onclick="close()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <form id="insert-form" enctype="multipart/form-data">
                    <!-- Title -->
                    <div class="form-group col-md-12">
                        <label for="add-title">Title</label>
                        <input id="add-title" type="text" name="title" class="form-control"
                               placeholder="Insert the film's title..." value="" required autocomplete="off">
                    </div>

                    <!-- Plot -->
                    <div class="form-group col-md-12">
                        <label for="add-plot">Plot:</label>
                        <textarea id="add-plot" class="form-control"
                                  name="plot"
                                  placeholder="Insert the film's plot..."
                                  required
                                  autocomplete="off"></textarea>
                    </div>
                    <!-- Genre  -->
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="add-genre">Genre</label>
                        <select class="form-control" id="add-genre" name="genre">
                            <option selected>Choose...</option>
                            <option value="1">Action</option>
                            <option value="2">Adventure</option>
                            <option value="3">Comedy</option>
                            <option value="4">Horror</option>
                            <option value="5">Romance</option>
                        </select>
                    </div>

                    <!-- Poster -->
                    <div>
                        <input type="file" id="file" name="poster" class="form-control" multiple />
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" value="Submit" class="btn btn-primary" data-dismiss="modal"
                        onclick="controller.insert()">Save
                    Changes
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Popup per la modifica -->
<div class="modal fade" id="edit-modal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Edit Film</h5>
                <button type="button" class="close" aria-label="Close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <form id="edit-form">
                    <!-- ID -->
                    <input id="edit-id" type="hidden" name="id" value="">

                    <!-- Title -->
                    <div class="form-group col-md-12">
                        <label for="edit-title">Title</label>
                        <input id="edit-title" type="text" name="title" class="form-control"
                               placeholder="Insert the movie's title..." value="" required autocomplete="off">
                    </div>

                    <!-- Plot -->
                    <div class="form-group col-md-12">
                        <label for="edit-plot">Plot</label>
                        <textarea id="edit-plot" class="form-control"
                                  name="plot"
                                  placeholder="Insert the film's plot..."
                                  required
                                  autocomplete="off"></textarea>
                    </div>

                    <!-- Genre -->
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="edit-genre">Genre</label>
                        <select class="form-select" id="edit-genre" name="genre">
                            <option selected>Choose...</option>
                            <option value="1">Action</option>
                            <option value="2">Adventure</option>
                            <option value="3">Comedy</option>
                            <option value="4">Horror</option>
                            <option value="5">Romance</option>
                        </select>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" value="Submit" class="btn btn-primary" data-dismiss="modal"
                        onclick="controller.edit()">Save
                    Changes
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Popup per la conferma della cancellazione -->
<div id="delete-modal" class="modal fade" role='dialog'>
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Delete</h5>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times</button>
            </div>
            <div class="modal-body">
                <p>Do You Really Want to Delete <strong id="delete-name"></strong> ?</p>
                <form id="delete-form">
                    <input id="delete-id" type="hidden" name="id" value="">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel
                </button>
                <button id='confirmDelete' type="submit" value="Submit" class="btn btn-danger" data-dismiss="modal"
                        onclick="controller.delete()">Confirm
                </button>
            </div>
        </div>
    </div>
</div>


<!-- JQuery api -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.js"></script>
<!-- Bootstrap api -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>

<!-- Custom js -->
<script src="../js/FilmAdding.js" charset="utf-8"></script>
<script src="../js/FormUtil.js" charset="utf-8"></script>

<script type="application/javascript">

    // Instance controller on this page
    const controller = new FilmAdding();

    // When document is ready, get data and fill table
    $(document).ready(function () {
        controller.fillTable();
    });

</script>

</body>
</html>