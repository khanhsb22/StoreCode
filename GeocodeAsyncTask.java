private void geoLocate() {
        String searchString = edtSearch.getText().toString().trim();
        new GeocodeAsyncTask().execute(searchString);
}

class GeocodeAsyncTask extends AsyncTask<String, Void, Address> {
        String errorMessage = "";
        @Override
        protected Address doInBackground(String... arg) {
            Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(Arrays.toString(arg), 1);
            } catch (IOException e) {
                errorMessage = "Service Not Available";
                Log.e(TAG, errorMessage, e);
                throw new RuntimeException(e);
            }

            if (addresses.size() > 0) {
                return addresses.get(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Address address) {
            if (address == null) {
                Log.e(TAG, "onPostExecute: " + errorMessage);
            } else {
                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                        address.getAddressLine(0));
                Log.d(TAG, "onPostExecute: Found a location: " + address);
            }
        }
    }