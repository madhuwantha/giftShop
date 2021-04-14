import React from 'react';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import ShopHome from 'app/modules/client/shop-home/shop-home';
import GiftDetail from 'app/modules/client/gift-detail/gift-detail';
import ClientCart from 'app/modules/client/cart/client-cart';
import Checkout from 'app/modules/client/checkout/checkout';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute path={`${match.url}/home`} component={ShopHome} />
    <ErrorBoundaryRoute path={`${match.url}/detail`} component={GiftDetail} />
    <ErrorBoundaryRoute path={`${match.url}/cart`} component={ClientCart} />
    <ErrorBoundaryRoute path={`${match.url}/checkout`} component={Checkout} />
  </div>
);

export default Routes;
