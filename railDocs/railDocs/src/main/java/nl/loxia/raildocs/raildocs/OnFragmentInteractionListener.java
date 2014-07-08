package nl.loxia.raildocs.raildocs;

/**
* Created by Tiemen on 8-7-2014.
*/
public interface OnFragmentInteractionListener {
    public void postGeselecteerd(String post);
    public void dossierGeselecteerd(String dossier);
    public void documentGeselecteerd(String document, boolean popFromBackstack);
    public void nearbyItemGeselecteerd(String post, String dossier, String document);
}
