package net.christosav.mpos.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import net.christosav.mpos.data.User;
import net.christosav.mpos.security.AuthenticatedUser;
import net.christosav.mpos.views.about.AboutView;
import net.christosav.mpos.views.cart.CartView;
import net.christosav.mpos.views.catalog.CatalogView;
import net.christosav.mpos.views.catalog.categories.CategoryView;
import net.christosav.mpos.views.catalog.imageEntities.ImageView;
import net.christosav.mpos.views.catalog.orderableitems.OrderableItemView;
import net.christosav.mpos.views.checkoutform.CheckoutFormView;
import net.christosav.mpos.views.gridwithfilters.GridwithFiltersView;
import net.christosav.mpos.views.helloworld.HelloWorldView;
import net.christosav.mpos.views.myview.MyViewView;
import net.christosav.mpos.views.myview2.MyView2View;
import net.christosav.mpos.views.pos.OrderEditorView;
import net.christosav.mpos.views.pos.OrderHistoryView;
import net.christosav.mpos.views.products.ProductsView;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("My App");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (accessChecker.hasAccess(ImageView.class)) {
            nav.addItem(new SideNavItem("Images", ImageView.class, LineAwesomeIcon.CALENDAR_TIMES.create()));
        }

        if (accessChecker.hasAccess(CategoryView.class)) {
            nav.addItem(new SideNavItem("Categories", CategoryView.class, LineAwesomeIcon.CALENDAR_TIMES.create()));
        }

        if (accessChecker.hasAccess(OrderableItemView.class)) {
            nav.addItem(new SideNavItem("OrderableItem", OrderableItemView.class, LineAwesomeIcon.CALENDAR_TIMES.create()));
        }

        if (accessChecker.hasAccess(OrderEditorView.class)) {
            nav.addItem(new SideNavItem("New Order", OrderEditorView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create()));
        }

        if (accessChecker.hasAccess(OrderHistoryView.class)) {
            nav.addItem(new SideNavItem("Order History", OrderHistoryView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create()));
        }

        if (accessChecker.hasAccess(CartView.class)) {
            nav.addItem(new SideNavItem("Cart", CartView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create()));
        }

        if (accessChecker.hasAccess(HelloWorldView.class)) {
            nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));

        }
        if (accessChecker.hasAccess(AboutView.class)) {
            nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));

        }
        if (accessChecker.hasAccess(MyViewView.class)) {
            nav.addItem(new SideNavItem("My View", MyViewView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        }
        if (accessChecker.hasAccess(ProductsView.class)) {
            nav.addItem(new SideNavItem("Products", ProductsView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        }
        if (accessChecker.hasAccess(CatalogView.class)) {
            nav.addItem(new SideNavItem("Catalog", CatalogView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        }
        if (accessChecker.hasAccess(CheckoutFormView.class)) {
            nav.addItem(new SideNavItem("Checkout Form", CheckoutFormView.class, LineAwesomeIcon.CREDIT_CARD.create()));

        }
        if (accessChecker.hasAccess(GridwithFiltersView.class)) {
            nav.addItem(new SideNavItem("Grid with Filters", GridwithFiltersView.class,
                    LineAwesomeIcon.ADDRESS_BOOK.create()));

        }

        if (accessChecker.hasAccess(MyView2View.class)) {
            nav.addItem(new SideNavItem("My View2", MyView2View.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
