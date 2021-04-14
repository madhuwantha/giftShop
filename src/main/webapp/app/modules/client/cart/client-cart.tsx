import React from 'react';

import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from 'app/entities/category/category.reducer';
import { Button } from 'reactstrap';

export interface IShopCartProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

const ClientCart = (props: IShopCartProps) => {
  const { match } = props;

  return (
    <div className="cart-main-wrapper mt-no-text">
      <div className="container custom-area">
        <div className="row">
          <div className="col-lg-12 col-custom">
            <div className="cart-table table-responsive">
              <table className="table table-bordered">
                <thead>
                  <tr>
                    <th className="pro-thumbnail">Image</th>
                    <th className="pro-title">Product</th>
                    <th className="pro-price">Price</th>
                    <th className="pro-quantity">Quantity</th>
                    <th className="pro-subtotal">Total</th>
                    <th className="pro-remove">Remove</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td className="pro-thumbnail">
                      <a href="#">
                        <img className="img-fluid" src="../../../content/images/product/small-size/1.jpg" alt="Product" />
                      </a>
                    </td>
                    <td className="pro-title">
                      <a href="#">Pearly Everlasting s / green</a>
                    </td>
                    <td className="pro-price">
                      <span>$295.00</span>
                    </td>
                    <td className="pro-quantity">
                      <div className="quantity">
                        <div className="cart-plus-minus">
                          <input className="cart-plus-minus-box" value="0" type="text" />
                        </div>
                      </div>
                    </td>
                    <td className="pro-subtotal">
                      <span>$295.00</span>
                    </td>
                    <td className="pro-remove">
                      <a href="#">
                        <i className="lnr lnr-trash"></i>
                      </a>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-lg-5 ml-auto col-custom">
            <div className="cart-calculator-wrapper">
              <div className="cart-calculate-items">
                <h3>Cart Totals</h3>
                <div className="table-responsive">
                  <table className="table">
                    <tr>
                      <td>Sub Total</td>
                      <td>$230</td>
                    </tr>
                    <tr>
                      <td>Shipping</td>
                      <td>$70</td>
                    </tr>
                    <tr className="total">
                      <td>Total</td>
                      <td className="total-amount">$300</td>
                    </tr>
                  </table>
                </div>
              </div>
              <Button
                className="btn flosun-button primary-btn rounded-0 black-btn w-100"
                tag={Link}
                to={`${match.url.slice(0, -5)}/checkout`}
                color="info"
                size="sm"
                data-cy="entityDetailsButton"
              >
                Proceed Checkout
              </Button>
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

export default connect(mapStateToProps, mapDispatchToProps)(ClientCart);
