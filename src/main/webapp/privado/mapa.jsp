<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Mapa de Zonas</title>

        <!-- Leaflet -->
        <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css"/>
        <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>

        <style>
            #map {
                height: 500px;
                width: 100%;
            }
        </style>
    </head>

    <body>

        <h2>Zonas registradas</h2>

        <div id="map"></div>

        <hr>

        <form action="${pageContext.request.contextPath}/privado/SvZona/crear" method="POST">
            <div class="form-group row">

                <div class="col-sm-4 mb-3">
                    <input type="text"
                           class="form-control"
                           id="nombreZona"
                           name="nombreZona"
                           placeholder="Nombre de la zona"
                           required>
                </div>

                <div class="col-sm-4 mb-3">
                    <input type="text"
                           class="form-control"
                           id="lat"
                           name="lat"
                           readonly
                           placeholder="Latitud">
                </div>

                <div class="col-sm-4 mb-3">
                    <input type="text"
                           class="form-control"
                           id="lng"
                           name="lng"
                           readonly
                           placeholder="Longitud">
                </div>

                <div class="col-sm-4 mb-3">
                    <button class="btn btn-primary" type="submit">
                        Guardar Zona
                    </button>
                </div>

            </div>
        </form>

        <!-- PASAR ZONAS A JAVASCRIPT -->
        <script>
            var zonas = [
            <c:forEach var="z" items="${listaZonas}" varStatus="st">
            {
    id: ${z.id},
                    localizacion: "${z.localizacion}"
            }${!st.last ? ',' : ''}
            </c:forEach>
            ];
            <%%>
            var zonaFocusId = "${zonaFocusId}";
        </script>

        <!-- MAPA -->
        <script>
            var map = L.map('map').setView([-27.3827, -55.9197], 12);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19
            }).addTo(map);

            var bounds = [];
            var markersPorId = {};

            // Dibujar zonas existentes
            zonas.forEach(function (z) {

                var partes = z.localizacion.split(" / ");
                if (partes.length !== 2)
                    return;

                var nombre = partes[0];
                var coords = partes[1].split(",");
                if (coords.length !== 2)
                    return;

                var lat = parseFloat(coords[0]);
                var lng = parseFloat(coords[1]);
                if (isNaN(lat) || isNaN(lng))
                    return;

                var marker = L.marker([lat, lng])
                        .addTo(map)
                        .bindPopup("<b>" + nombre + "</b>");

                markersPorId[z.id] = marker;
                bounds.push([lat, lng]);
            });

            if (bounds.length > 0) {
                map.fitBounds(bounds);
            }

            // FLY TO POR ID
            if (zonaFocusId && markersPorId[zonaFocusId]) {
                var m = markersPorId[zonaFocusId];

                map.flyTo(
                        m.getLatLng(),
                        16,
                        {duration: 1.2}
                );

                m.openPopup();
            }

            // Marker nuevo
            var markerNuevo = null;

            map.on('click', function (e) {
                var lat = e.latlng.lat;
                var lng = e.latlng.lng;

                document.getElementById("lat").value = lat;
                document.getElementById("lng").value = lng;

                if (markerNuevo) {
                    markerNuevo.setLatLng(e.latlng);
                } else {
                    markerNuevo = L.marker([lat, lng], {draggable: true}).addTo(map);

                    markerNuevo.on('dragend', function (ev) {
                        var pos = ev.target.getLatLng();
                        document.getElementById("lat").value = pos.lat;
                        document.getElementById("lng").value = pos.lng;
                    });
                }
            });
        </script>

    </body>
</html>
