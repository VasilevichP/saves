import java.util.ArrayList;

public abstract class Menu {
    private String header;
    private ArrayList<MenuItem> items;
    private boolean isFinished;

    Menu(String title) {
        this.header = title;
        items = new ArrayList<MenuItem>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void show() {
        System.out.println(header);
        while (!isFinished) {
            showItems();
            if (!items.get(selectItem() - 1).select()) {
                finish();
            }
        }
    }

    private void showItems() {
        int i = 1;
        for (MenuItem it : items) {
            System.out.println(i + " - " + it.show());
            i++;
        }
    }

    private byte selectItem() {
        return SafeInput.getLimitedByte(getMenuLength());
    }

    private byte getMenuLength() {
        return (byte) items.size();
    }

    protected void finish() {
        isFinished = true;
    }
}

class AuthorizationMenu extends Menu {
    AuthorizationMenu(String title, AccountList accounts) {
        super(title);
        addItem(new MenuItem(
                new Command() {
                    @Override
                    public boolean execute() {
                        Account a = Account.signIn();
                        if (a != null) {
                            accounts.add(a);
                            return false;
                        } else return true;
                    }
                }, "Авторизоваться"));
        addItem(new MenuItem(
                new Command() {
                    @Override
                    public boolean execute() {
                        Account a = Account.signUp();
                        if (a != null) {
                            accounts.add(a);
                            return false;
                        } else return true;
                    }
                }, "Зарегистрироваться"));
        addItem(new MenuItem(new ExitMenuCommand(), "Выйти"));
    }
}

class FilterMenu extends Menu {
    FilterMenu(String title, TripFilter filter, Account account) {
        super(title);

        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                new TypeFilterMenu("Введите тип путевки, по которому будет производиться фильтрация:", filter).show();
                filter.apply();
                filter.displayList();
                return true;
            }
        }, "Отфильтровать путевки по типу"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                new CountryFilterMenu("Введите страну, по которой будет производиться фильтрация:", filter).show();
                filter.apply();
                filter.displayList();
                return true;
            }
        }, "Отфильтровать путевки по стране"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                new TransportFilterMenu("Введите тип транспорта, по которому будет производиться фильтрация:", filter).show();
                filter.apply();
                filter.displayList();
                return true;
            }
        }, "Отфильтровать путевки по типу транспорта"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.removeAllFilters();
                filter.apply();
                filter.displayList();
                return true;
            }
        }, "Убрать все фильтры"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.displayList();
                return true;
            }
        }, "Показать отфильтрованные путевки"));
        if (account instanceof User) {
            addItem(new MenuItem(new Command() {
                @Override
                public boolean execute() {
                    account.payTrips(filter.toList());
                    return false;
                }
            }, "Оплатить отфильтрованные путевки"));
        }
        addItem(new MenuItem(new ExitMenuCommand(), "Выйти из меню фильтрации"));
    }
}

class SortFilteredListMenu extends Menu {
    SortFilteredListMenu(String title, TripSorter sorter) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortRun());
                sorter.setComparator(TripComparator.ComparatorByNODays());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по кол-ву дней(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNODays().reversed());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по кол-ву дней(по убыванию) "));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOMeals());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по кол-ву приемов пищи(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOMeals().reversed());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по кол-ву приемов пищи(по убыванию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOPeople());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по кол-ву людей(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOPeople().reversed());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по кол-ву людей(по убыванию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByPrice());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по цене(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByPrice().reversed());
                sorter.display();
                return false;
            }
        }, "Отсортировать путевки по цене(по убыванию)"));
    }
}

class SortMenu extends Menu {
    SortMenu(String title, TripSorter sorter) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortRun());
                sorter.setComparator(TripComparator.ComparatorByNODays());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по кол-ву дней(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNODays().reversed());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по кол-ву дней(по убыванию) "));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOMeals());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по кол-ву приемов пищи(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOMeals().reversed());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по кол-ву приемов пищи(по убыванию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOPeople());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по кол-ву людей(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByNOPeople().reversed());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по кол-ву людей(по убыванию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByPrice());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по цене(по возрастанию)"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                sorter.setSorter(new SortThread());
                sorter.setComparator(TripComparator.ComparatorByPrice().reversed());
                sorter.display();
                return true;
            }
        }, "Отсортировать путевки по цене(по убыванию)"));
        addItem(new MenuItem(new ExitMenuCommand(), "Выйти из меню сортировки"));
    }
}

class AdminMenu extends Menu {
    AdminMenu(String title, Admin account) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.tripList.addTrip();
                return true;
            }
        }, "Добавить путевку"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.tripList.showTrips();
                return true;
            }
        }, "Просмотреть все добавленные путевки "));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.filterMenu();
                return true;
            }
        }, "Отфильтровать добавленные путевки"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.sortMenu();
                return true;
            }
        }, "Отсортировать добавленные путевки"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.accountMenu();
                return true;
            }
        }, "Работа с аккаунтами"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.tripList.calculation();
                return true;
            }
        }, "Вывести сумму всех путевок"));
        addItem(new MenuItem(new ExitMenuCommand(), "Выйти из меню администратора"));
    }
}

class AccountMenu extends Menu {
    AccountMenu(String title, Admin account) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.accountList.showAccounts();
                return true;
            }
        }, "Просмотреть все аккаунты "));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.accountList.showBlockedAccounts();
                return true;
            }
        }, "Просмотреть заблокированные аккаунты "));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.accountList.deleteAccount();
                return true;
            }
        }, "Удалить аккаунт"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.accountList.changeBlockAccount();
                return true;
            }
        }, "Изменить статус блокировки аккаунта"));
        addItem(new MenuItem(new ExitMenuCommand(), "Выйти из меню работы с аккаунтами"));
    }
}

class UserMenu extends Menu {
    UserMenu(String title, User account) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.tripList.showTrips();
                return true;
            }
        }, "Просмотреть все добавленные путевки "));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.filterMenu();
                return true;
            }
        }, "Отфильтровать добавленные путевки"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.sortMenu();
                return true;
            }
        }, "Отсортировать добавленные путевки"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                account.tripList.chooseTrip();
                return true;
            }
        }, "Выбрать путевку для оплаты"));
        addItem(new MenuItem(new ExitMenuCommand(), "Выйти из меню пользователя"));
    }
}

class TypeFilterMenu extends Menu {
    TypeFilterMenu(String title, TripFilter filter) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByType("Отдых");
                return false;
            }
        }, "\tОтдых"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByType("Экскурсия");
                return false;
            }
        }, "\tЭкскурсия"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByType("Шопинг");
                return false;
            }
        }, "\tШопинг"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByType("Лечение");
                return false;
            }
        }, "\tЛечение"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByType("Круиз");
                return false;
            }
        }, "\tКруиз"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByType("Company special");
                return false;
            }
        }, "\tCompany special"));
    }
}

class CountryFilterMenu extends Menu {
    CountryFilterMenu(String title, TripFilter filter) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Греция");
                return false;
            }
        }, "\tГреция"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Египет");
                return false;
            }
        }, "\tЕгипет"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Турция");
                return false;
            }
        }, "\tТурция"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Франция");
                return false;
            }
        }, "\tФранция"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Германия");
                return false;
            }
        }, "\tГермания"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Внутри страны");
                return false;
            }
        }, "\tВнутри страны"));
    }
}

class TransportFilterMenu extends Menu {
    TransportFilterMenu(String title, TripFilter filter) {
        super(title);
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Самолет");
                return false;
            }
        }, "\tСамолет"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Поезд");
                return false;
            }
        }, "\tПоезд"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Автобус");
                return false;
            }
        }, "\tАвтобус"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Автомобиль");
                return false;
            }
        }, "\tАвтомобиль"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("Паром");
                return false;
            }
        }, "\tПаром"));
        addItem(new MenuItem(new Command() {
            @Override
            public boolean execute() {
                filter.setByCountry("самостоятельное передвижение");
                return false;
            }
        }, "\tсамостоятельное передвижение"));
    }
}