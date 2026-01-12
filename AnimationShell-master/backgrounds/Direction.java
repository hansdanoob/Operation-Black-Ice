enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public Direction opposite() {
        if (this == NORTH) {
            return SOUTH;
        } else if (this == SOUTH) {
            return NORTH;
        } else if (this == EAST) {
            return WEST;
        } else if (this == WEST) {
            return EAST;
        } else {
            return null;
        }
    }
}