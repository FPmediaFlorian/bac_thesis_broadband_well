# Implementation Details 2020-01-23

## Frameworks, Dependencies and more
### Frontend
- Bootstrap
- Fontawesome Icons
#### Routeplaner:
- Leaflet
- LRM (Leaflet Routing Maschine)
	- LRM Graphhopper
- Maptiler Geocoder?? (Autocompletion for addresses)
- **Librespeed Speedtest**
	- runs with a Server in Bologna (fastest from my flat)
	- -\> copy files on uni Server or CDN? Or Serverside on URL
	- tested various other
#### IsochroneMap
- graphhopper API - 
- Targogmo - can not handle Isochrone with Free license (to many request points)
- ISO4APP ??
-  **Mapbox API - only supports one Marker** 
### Backend
#### Routeplaner
- graphhopper Routing (works okay with right coordinates)
- graphhopper Geocoding (unfortunately returned wrong lat&lng)
- Opencage geocoding (works fine so far)

## ToDo and next steps:
### Frontend:
- fill index.jsp
- Geocoding or autocompletion in Frontend?
- responsive Testing
	- Footer??
	- Isochrone Map
- Current location automatic
- legal Documents
### Backend:
- JavaDoc
- Logs to the right files

## Fragen:
- M3 presentation
- which protoype to use?