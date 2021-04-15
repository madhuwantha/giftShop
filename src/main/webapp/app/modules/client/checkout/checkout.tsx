import React, { useEffect, useState } from 'react';

import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { IRootState } from 'app/shared/reducers';
import { getEntityByUser } from 'app/entities/cart/cart.reducer';
import { getClientByUser } from 'app/entities/client/client.reducer';

export interface IShopCartProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

const Checkout = (props: IShopCartProps) => {
  const { client, account, cart } = props;
  const [totalPrice, setTotalPrice] = useState(0);

  useEffect(() => {
    props.getClientByUser(account.id);
  }, [account]);

  useEffect(() => {
    setTotalPrice(cart.giftItems.map<number>(gift => gift.unitPrice).reduce((a, b) => a + b, 0));
  }, [cart]);

  useEffect(() => {
    props.getEntityByUser(account.id);
  }, [account]);

  return (
    <div className="checkout-area mt-no-text">
      <div className="container custom-container">
        <div className="row">
          <div className="col-12 col-custom"></div>
        </div>
        <div className="row">
          <div className="col-lg-6 col-12 col-custom">
            <form action="#">
              <div className="checkbox-form">
                <h3>Billing Details</h3>
                <div className="row">
                  <div className="col-md-12 col-custom"></div>
                  <div className="col-md-12 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Name <span className="required">*</span>
                      </label>
                      <input placeholder="" type="text" value={client.name} />
                    </div>
                  </div>
                  <div className="col-md-12 col-custom"></div>
                  <div className="col-md-12 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Address <span className="required">*</span>
                      </label>
                      <input value={client.addressLineOne} placeholder="Street address" type="text" />
                    </div>
                  </div>
                  <div className="col-md-12 col-custom">
                    <div className="checkout-form-list">
                      <input value={client.addressLineTwo} placeholder="Apartment, suite, unit etc. (optional)" type="text" />
                    </div>
                  </div>
                  <div className="col-md-12 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Town / City <span className="required">*</span>
                      </label>
                      <input type="text" value={client.city} />
                    </div>
                  </div>
                  <div className="col-md-12 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Postcode / Zip <span className="required">*</span>
                      </label>
                      <input placeholder="" type="text" value={client.postalCode} />
                    </div>
                    {/*{client.}*/}
                  </div>
                </div>
                <div className="different-address">
                  <div id="ship-box-info" className="row mt-2">
                    <div className="col-md-12 col-custom">
                      <div className="myniceselect country-select clearfix">
                        <label>
                          Country <span className="required">*</span>
                        </label>
                        <select className="myniceselect nice-select wide rounded-0">
                          <option data-display="Bangladesh">Bangladesh</option>
                          <option value="uk">London</option>
                          <option value="rou">Romania</option>
                          <option value="fr">French</option>
                          <option value="de">Germany</option>
                          <option value="aus">Australia</option>
                        </select>
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>
                          First Name <span className="required">*</span>
                        </label>
                        <input placeholder="" type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>
                          Last Name <span className="required">*</span>
                        </label>
                        <input placeholder="" type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>Company Name</label>
                        <input placeholder="" type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>
                          Address <span className="required">*</span>
                        </label>
                        <input placeholder="Street address" type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <input placeholder="Apartment, suite, unit etc. (optional)" type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>
                          Town / City <span className="required">*</span>
                        </label>
                        <input type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>
                          State / County <span className="required">*</span>
                        </label>
                        <input placeholder="" type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>
                          Postcode / Zip <span className="required">*</span>
                        </label>
                        <input placeholder="" type="text" />
                      </div>
                    </div>
                    <div className="col-md-12 col-custom">
                      <div className="checkout-form-list">
                        <label>
                          Phone <span className="required">*</span>
                        </label>
                        <input type="text" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>
          <div className="col-lg-6 col-12 col-custom">
            <div className="your-order">
              <h3>Your order</h3>
              <div className="your-order-table table-responsive">
                <table className="table">
                  <thead>
                    <tr>
                      <th className="cart-product-name">Product</th>
                      <th className="cart-product-total">Price</th>
                    </tr>
                  </thead>
                  <tbody>
                    {cart.giftItems != undefined ? (
                      cart.giftItems.map(giftItem => {
                        return (
                          <tr className="cart_item">
                            <td className="cart-product-name"> {giftItem.giftName}</td>
                            <td className="cart-product-total text-center">
                              <span className="amount">$ {giftItem.unitPrice}</span>
                            </td>
                          </tr>
                        );
                      })
                    ) : (
                      <></>
                    )}
                  </tbody>
                  <tfoot>
                    <tr className="order-total">
                      <th>Order Total</th>
                      <td className="text-center">
                        <strong>
                          <span className="amount">£ {totalPrice}</span>
                        </strong>
                      </td>
                    </tr>
                  </tfoot>
                </table>
              </div>
              <div className="payment-method">
                <div className="payment-accordion">
                  <div id="accordion">
                    <div className="card">
                      <div className="card-header" id="#payment-1">
                        <h5 className="panel-title mb-3">
                          <a
                            href="#"
                            className=""
                            data-toggle="collapse"
                            data-target="#collapseOne"
                            aria-expanded="true"
                            aria-controls="collapseOne"
                          >
                            Direct Bank Transfer.
                          </a>
                        </h5>
                      </div>
                      <div id="collapseOne" className="collapse show" data-parent="#accordion">
                        <div className="card-body mb-2 mt-2">
                          <p>
                            Make your payment directly into our bank account. Please use your Order ID as the payment reference. Your order
                            won’t be shipped until the funds have cleared in our account.
                          </p>
                        </div>
                      </div>
                    </div>
                    <div className="card">
                      <div className="card-header" id="#payment-2"></div>
                      <div id="collapseTwo" className="collapse" data-parent="#accordion">
                        <div className="card-body mb-2 mt-2">
                          <p>
                            Make your payment directly into our bank account. Please use your Order ID as the payment reference. Your order
                            won’t be shipped until the funds have cleared in our account.
                          </p>
                        </div>
                      </div>
                    </div>
                    <div className="card">
                      <div className="card-header" id="#payment-3"></div>
                      <div id="collapseThree" className="collapse" data-parent="#accordion">
                        <div className="card-body mb-2 mt-2">
                          <p>
                            Make your payment directly into our bank account. Please use your Order ID as the payment reference. Your order
                            won’t be shipped until the funds have cleared in our account.
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="order-button-payment">
                    <button className="btn flosun-button secondary-btn black-color rounded-0 w-100">Place Order</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({ cart, authentication, client }: IRootState) => ({
  account: authentication.account,
  cart: cart.entity,
  client: client.entity,
});

const mapDispatchToProps = {
  getEntityByUser,
  getClientByUser,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Checkout);
