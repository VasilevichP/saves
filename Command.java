public abstract class Command {
    Command(){}
    public abstract boolean execute();
}

abstract class TripListCommand extends Command {
    protected TripList tripList;

    TripListCommand(TripList tripList) {
        this.tripList = tripList;
    }
}

class ExitMenuCommand extends Command{
    @Override
    public boolean execute() {
        return false;
    }
}
class AddTripCommand extends TripListCommand {
    AddTripCommand(TripList tripList) {
        super(tripList);
    }

    public boolean execute() {
        tripList.addTrip();
        return true;
    }
}
