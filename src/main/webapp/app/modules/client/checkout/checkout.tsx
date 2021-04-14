import React from 'react';

import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from 'app/entities/category/category.reducer';

export interface IShopCartProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

const Checkout = (props: IShopCartProps) => {
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
                  <div className="col-md-12 col-custom">
                    {/*<div className="country-select clearfix">*/}
                    {/*  <label>Country <span className="required">*</span></label>*/}
                    {/*  <select className="myniceselect nice-select wide rounded-0">*/}
                    {/*    <option data-display="Bangladesh">Bangladesh</option>*/}
                    {/*    <option value="uk">London</option>*/}
                    {/*    <option value="rou">Romania</option>*/}
                    {/*    <option value="fr">French</option>*/}
                    {/*    <option value="de">Germany</option>*/}
                    {/*    <option value="aus">Australia</option>*/}
                    {/*  </select>*/}
                    {/*</div>*/}
                  </div>
                  <div className="col-md-6 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        First Name <span className="required">*</span>
                      </label>
                      <input placeholder="" type="text" />
                    </div>
                  </div>
                  <div className="col-md-6 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Last Name <span className="required">*</span>
                      </label>
                      <input placeholder="" type="text" />
                    </div>
                  </div>
                  <div className="col-md-12 col-custom">
                    {/*<div className="checkout-form-list">*/}
                    {/*  <label>Company Name</label>*/}
                    {/*  <input placeholder="" type="text" />*/}
                    {/*</div>*/}
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
                  <div className="col-md-6 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        State <span className="required">*</span>
                      </label>
                      <input placeholder="" type="text" />
                    </div>
                  </div>
                  <div className="col-md-6 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Postcode / Zip <span className="required">*</span>
                      </label>
                      <input placeholder="" type="text" />
                    </div>
                  </div>
                  <div className="col-md-6 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Email Address <span className="required">*</span>
                      </label>
                      <input placeholder="" type="email" />
                    </div>
                  </div>
                  <div className="col-md-6 col-custom">
                    <div className="checkout-form-list">
                      <label>
                        Phone <span className="required">*</span>
                      </label>
                      <input type="text" />
                    </div>
                  </div>
                  <div className="col-md-12 col-custom">
                    {/*<div className="checkout-form-list create-acc">*/}
                    {/*  <input id="cbox" type="checkbox" />*/}
                    {/*    <label htmlFor="cbox">Create an account?</label>*/}
                    {/*</div>*/}
                    <div id="cbox-info" className="checkout-form-list create-account">
                      <p className="mb-2">
                        Create an account by entering the information below. If you are a returning customer please login at the top of the
                        page.
                      </p>
                      <label>
                        Account password <span className="required">*</span>
                      </label>
                      <input placeholder="password" type="password" />
                    </div>
                  </div>
                </div>
                <div className="different-address">
                  {/*<div className="ship-different-title">*/}
                  {/*  <div>*/}
                  {/*    <input id="ship-box" type="checkbox" />*/}
                  {/*      <label htmlFor="ship-box">Ship to a different address?</label>*/}
                  {/*  </div>*/}
                  {/*</div>*/}
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
                          Email Address <span className="required">*</span>
                        </label>
                        <input placeholder="" type="email" />
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
                  <div className="order-notes mt-3">
                    <div className="checkout-form-list checkout-form-list-2">
                      <label>Order Notes</label>
                      {/*<textarea id="checkout-mess" cols="30" rows="10"*/}
                      {/*          placeholder="Notes about your order, e.g. special notes for delivery."></textarea>*/}
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
                      <th className="cart-product-total">Total</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr className="cart_item">
                      <td className="cart-product-name">
                        {' '}
                        Vestibulum suscipit<strong className="product-quantity">× 1</strong>
                      </td>
                      <td className="cart-product-total text-center">
                        <span className="amount">£165.00</span>
                      </td>
                    </tr>
                    <tr className="cart_item">
                      <td className="cart-product-name">
                        {' '}
                        Vestibulum suscipit<strong className="product-quantity">× 1</strong>
                      </td>
                      <td className="cart-product-total text-center">
                        <span className="amount">£165.00</span>
                      </td>
                    </tr>
                  </tbody>
                  <tfoot>
                    <tr className="cart-subtotal">
                      <th>Cart Subtotal</th>
                      <td className="text-center">
                        <span className="amount">£215.00</span>
                      </td>
                    </tr>
                    <tr className="order-total">
                      <th>Order Total</th>
                      <td className="text-center">
                        <strong>
                          <span className="amount">£215.00</span>
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
                      <div className="card-header" id="#payment-2">
                        {/*<h5 className="panel-title mb-3">*/}
                        {/*  <a href="#" className="collapsed" data-toggle="collapse" data-target="#collapseTwo"*/}
                        {/*     aria-expanded="false" aria-controls="collapseTwo">*/}
                        {/*    Cheque Payment*/}
                        {/*  </a>*/}
                        {/*</h5>*/}
                      </div>
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
                      <div className="card-header" id="#payment-3">
                        {/*<h5 className="panel-title mb-3">*/}
                        {/*  <a href="#" className="collapsed" data-toggle="collapse" data-target="#collapseThree"*/}
                        {/*     aria-expanded="false" aria-controls="collapseThree">*/}
                        {/*    PayPal*/}
                        {/*  </a>*/}
                        {/*</h5>*/}
                      </div>
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

const mapStateToProps = ({ category }: IRootState) => ({
  categoryList: category.entities,
  loading: category.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Checkout);
