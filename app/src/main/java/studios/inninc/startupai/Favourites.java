package studios.inninc.startupai;

/**
 * Created by Pradyumna1 on 1/22/2017.
 */
public class Favourites {

    int _id;
    String _event;
    String _cat;

    public Favourites() {
    }

    public Favourites(int _id, String _event, String _cat) {
        this._id = _id;
        this._event = _event;
        this._cat = _cat;
    }

    public Favourites(String _event, String _cat) {
        this._event = _event;
        this._cat = _cat;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_event() {
        return _event;
    }

    public void set_event(String _event) {
        this._event = _event;
    }

    public String get_cat() {
        return _cat;
    }

    public void set_cat(String _cat) {
        this._cat = _cat;
    }
}
