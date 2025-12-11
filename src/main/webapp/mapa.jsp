<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Mapa con Leaflet</title>

    <!-- CSS de leaflet -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css"/>

    <!-- JS de leaflet -->
    <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>

    <style>
        #map { height: 500px; }
    </style>
</head>
<body>
<h2>Seleccionar punto en el mapa</h2>

<div id="map" class="mb-4"></div>

<form action="SvZona/crear" method="POST">
    <div class="form-group row">
        <div class="col-sm-4 mb-3">
            <input type="text" class="form-control form-control-user" id="nombreZona" name="nombreZona"
                   placeholder="Nombre Zona" required>
        </div>
        <div class="col-sm-4 mb-3">
            <input type="text" class="form-control form-control-user" id="lat" name="lat" readonly>
        </div>
        <div class="col-sm-4 mb-3">
            <input type="text" class="form-control form-control-user" id="lng" name="lng" readonly>
        </div>
        <div class="col-sm-4 mb-3">
            <button class="btn btn-primary btn-user btn-block" type="submit">Guardar</button>
        </div>
    </div>
    
</form>

<script>
    // Crear mapa
    var map = L.map('map').setView([-27.3827, -55.9197], 12); // CABA

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19
    }).addTo(map);
    
    var marker;

    // Click en el mapa
    map.on('click', function(e) {
        var lat = e.latlng.lat;
        var lng = e.latlng.lng;

        // Actualizar inputs
        document.getElementById("lat").value = lat;
        document.getElementById("lng").value = lng;

        // Mover marcador
        if (marker) {
            marker.setLatLng(e.latlng);
        } else {
            marker = L.marker([lat, lng]).addTo(map);
        }
    });
</script>
</body>
</html>
