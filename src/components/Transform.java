package components;

import structures.Vector;

public class Transform extends Component {
    private Vector localPosition;
    private Vector localScale;
    private double localRotation;

    public Transform() {
        this.localPosition = Vector.ZERO;
        this.localScale = Vector.ONE;
        this.localRotation = Math.PI / 2;
    }

    public Transform(Vector localPosition) {
        this.localPosition = localPosition;
        this.localScale = Vector.ONE;
        this.localRotation = Math.PI / 2;
    }

    public Transform(Vector localPosition, Vector size) {
        this.localPosition = localPosition;
        this.localScale = size;
        this.localRotation = Math.PI / 2;
    }

    public Vector getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(Vector position) {
        this.localPosition = position;
    }

    public Vector getPosition() {
        if (getGameObject().getParent() == null) {
            return localPosition;
        }

        Transform parentTransform = getGameObject().getParent().getComponent(Transform.class);
        if (parentTransform == null) {
            return localPosition;
        } else {
            return parentTransform.getPosition().add(localPosition);
        }
    }

    public void setPosition(Vector position) {
        if (getGameObject().getParent() == null) {
            this.localPosition = position;
        } else {
            Transform parentTransform = getGameObject().getParent().getComponent(Transform.class);
            if (parentTransform == null) {
                this.localPosition = position;
            } else {
                this.localPosition = position.subtract(parentTransform.getPosition());
            }
        }
    }

    public Vector getScreenPosition() {
        Camera camera = getGameObject().getScene().getComponent(Camera.class);
        return camera.worldToScreenPosition(getPosition());
    }

    public Vector getLocalScale() {
        return localScale;
    }

    public void setLocalScale(Vector size) {
        this.localScale = size;
    }

    public Vector getScale() {
        if (getGameObject().getParent() == null) {
            return localScale;
        }

        Transform parentTransform = getGameObject().getParent().getComponent(Transform.class);
        if (parentTransform == null) {
            return localScale;
        } else {
            return parentTransform.getScale().multiply(localScale);
        }
    }

    public void setScale(Vector scale) {
        if (getGameObject().getParent() == null) {
            this.localScale = scale;
        } else {
            Transform parentTransform = getGameObject().getParent().getComponent(Transform.class);
            if (parentTransform == null) {
                this.localScale = scale;
            } else {
                this.localScale = scale.divide(parentTransform.getScale());
            }
        }
    }

    public Vector getScreenScale() {
        Camera camera = getGameObject().getScene().getComponent(Camera.class);
        return camera.worldToScreenSize(getScale());
    }

    public double getLocalRotation() {
        return localRotation;
    }

    public void setLocalRotation(double rotation) {
        this.localRotation = rotation;
    }

    public double getRotation() {
        if (getGameObject().getParent() == null) {
            return localRotation;
        }

        Transform parentTransform = getGameObject().getParent().getComponent(Transform.class);
        if (parentTransform == null) {
            return localRotation;
        } else {
            return parentTransform.getRotation() + localRotation;
        }
    }

    public void setRotation(double rotation) {
        if (getGameObject().getParent() == null) {
            this.localRotation = rotation;
        } else {
            Transform parentTransform = getGameObject().getParent().getComponent(Transform.class);
            if (parentTransform == null) {
                this.localRotation = rotation;
            } else {
                this.localRotation = rotation - parentTransform.getRotation();
            }
        }
    }

    public double getLocalTop() {
        return localPosition.getY() + localScale.getY() / 2;
    }

    public void setLocalTop(double top) {
        localPosition = localPosition.withY(top - localScale.getY() / 2);
    }

    public double getTop() {
        return getPosition().getY() + getScale().getY() / 2;
    }

    public void setTop(double top) {
        setPosition(getPosition().withY(top - getScale().getY() / 2));
    }

    public double getLocalBottom() {
        return localPosition.getY() - localScale.getY() / 2;
    }

    public void setLocalBottom(double bottom) {
        localPosition = localPosition.withY(bottom + localScale.getY() / 2);
    }

    public double getBottom() {
        return getPosition().getY() - getScale().getY() / 2;
    }

    public void setBottom(double bottom) {
        setPosition(getPosition().withY(bottom + getScale().getY() / 2));
    }

    public double getLocalLeft() {
        return localPosition.getX() - localScale.getX() / 2;
    }

    public void setLocalLeft(double left) {
        localPosition = localPosition.withX(left + localScale.getX() / 2);
    }

    public double getLeft() {
        return getPosition().getX() - getScale().getX() / 2;
    }

    public void setLeft(double left) {
        setPosition(getPosition().withX(left + getScale().getX() / 2));
    }

    public double getLocalRight() {
        return localPosition.getX() + localScale.getX() / 2;
    }

    public void setLocalRight(double right) {
        localPosition = localPosition.withX(right - localScale.getX() / 2);
    }

    public double getRight() {
        return getPosition().getX() + getScale().getX() / 2;
    }

    public void setRight(double right) {
        setPosition(getPosition().withX(right - getScale().getX() / 2));
    }

}
