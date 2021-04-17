import React, { useEffect, useState } from 'react';

import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { IRootState } from 'app/shared/reducers';
import { getEntities } from 'app/entities/category/category.reducer';
import { getEntityByUser, removeFromCart } from 'app/entities/cart/cart.reducer';
import { Button } from 'reactstrap';
import { selectGiftIem } from 'app/entities/gift-item/gift-item.reducer';

export interface IShopCartProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

const ClientCart = (props: IShopCartProps) => {
  const { match, account, cart } = props;

  const [totalPrice, setTotalPrice] = useState(0);

  useEffect(() => {
    setTotalPrice(cart.giftItems != undefined ? cart.giftItems.map<number>(gift => gift.unitPrice).reduce((a, b) => a + b, 0) : 0);
  }, [cart]);

  function onProductClick(giftItem) {
    props.selectGiftIem(giftItem);
  }

  function onProductDeleteClick(giftItem) {
    props.removeFromCart(giftItem.id, account.id);
  }

  useEffect(() => {
    props.getEntityByUser(account.id);
  }, [account]);

  useEffect(() => {
    console.log(cart);
  }, [cart]);

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
                    <th className="pro-remove">Remove</th>
                  </tr>
                </thead>
                <tbody>
                  {cart.giftItems != undefined ? (
                    cart.giftItems.map(giftItem => {
                      return (
                        <tr>
                          <td className="pro-thumbnail">
                            <a href="#">
                              <img
                                className="img-fluid"
                                src={giftItem.image != undefined ? 'http://localhost:8080/public/image/' + giftItem.image.imagepath : ''}
                                alt="Product"
                              />
                            </a>
                          </td>
                          <td className="pro-title">
                            <Button
                              onClick={() => onProductClick(giftItem)}
                              title="Quick View"
                              data-toggle="modal"
                              data-target="#exampleModalCenter"
                              style={{ background: 'transparent', border: 'none', color: 'black' }}
                              tag={Link}
                              to={`${baseHref}/shop/detail`}
                            >
                              {giftItem.giftName}
                            </Button>
                            {/*<a href="#">{giftItem.giftName}</a>*/}
                          </td>
                          <td className="pro-price">
                            <span>$ {giftItem.unitPrice}</span>
                          </td>
                          <td className="pro-remove">
                            <Button
                              onClick={() => onProductDeleteClick(giftItem)}
                              style={{ background: 'transparent', border: 'none', color: 'black' }}
                            >
                              <i className="lnr lnr-trash"></i>
                            </Button>
                          </td>
                        </tr>
                      );
                    })
                  ) : (
                    <></>
                  )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-lg-5 ml-auto col-custom">
            <div className="cart-calculator-wrapper">
              <div className="cart-calculate-items">
                <h3>Cart Total</h3>
                <div className="table-responsive">
                  <table className="table">
                    {/*<tr>*/}
                    {/*  <td>Sub Total</td>*/}
                    {/*  <td>$230</td>*/}
                    {/*</tr>*/}
                    {/*<tr>*/}
                    {/*  <td>Shipping</td>*/}
                    {/*  <td>$70</td>*/}
                    {/*</tr>*/}
                    <tr className="total">
                      <td>Total</td>
                      <td className="total-amount">$ {totalPrice}</td>
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

const mapStateToProps = ({ authentication, cart }: IRootState) => {
  return {
    account: authentication.account,
    cart: cart.entity,
  };
};

const mapDispatchToProps = {
  getEntities,
  getEntityByUser,
  selectGiftIem,
  removeFromCart,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientCart);
