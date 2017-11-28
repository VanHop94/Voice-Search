package smarttouch.uet.edu.voicesearch.entities;

/**
 * Created by VanHop on 4/10/2016.
 */
public class CustomObject {

    private int type;
    private Object data;



    public CustomObject(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
