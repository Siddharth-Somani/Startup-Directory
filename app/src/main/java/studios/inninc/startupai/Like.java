package studios.inninc.startupai;

/**
 * Created by Pradyumna1 on 1/22/2017.
 */
public class Like {

    int _id;
    String _event;
    String _liked;

    public Like() {
    }

    public Like(String _event, String _liked) {
        this._event = _event;
        this._liked = _liked;
    }

    public Like(int _id, String _event, String _liked) {
        this._id = _id;
        this._event = _event;
        this._liked = _liked;
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

    public String get_liked() {
        return _liked;
    }

    public void set_liked(String _liked) {
        this._liked = _liked;
    }
}
