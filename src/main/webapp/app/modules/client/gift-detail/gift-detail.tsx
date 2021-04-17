import React from 'react';
import Any = jasmine.Any;
import { IRootState } from 'app/shared/reducers';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';

export interface IGiftDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

import { Button } from 'reactstrap';
import { addToCart } from 'app/entities/cart/cart.reducer';

const GiftDetail = (props: IGiftDetailProps) => {
  const { giftItem, account } = props;

  function onAddToCart() {
    props.addToCart(giftItem.id, account.id);
  }
  return (
    <div>
      <div className="single-product-main-area">
        <div className="container container-default custom-area">
          <div className="row">
            <div className="col-lg-5 offset-lg-0 col-md-8 offset-md-2 col-custom">
              <div className="product-details-img">
                <div className="single-product-img swiper-container gallery-top popup-gallery">
                  <div className="swiper-wrapper">
                    <div className="swiper-slide">
                      <a className="w-100" href="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png">
                        <img
                          className="w-100"
                          src={giftItem.image != undefined ? 'http://localhost:8080/public/image/' + giftItem.image.imagepath : ''}
                          alt="Product"
                        />
                      </a>
                    </div>
                    {/*<div className="swiper-slide">*/}
                    {/*  <a className="w-100" href="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png">*/}
                    {/*    <img*/}
                    {/*      className="w-100"*/}
                    {/*      src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"*/}
                    {/*      alt="Product"*/}
                    {/*    />*/}
                    {/*  </a>*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <a className="w-100" href="assets/images/product/large-size/3.jpg">*/}
                    {/*    <img*/}
                    {/*      className="w-100"*/}
                    {/*      src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"*/}
                    {/*      alt="Product"*/}
                    {/*    />*/}
                    {/*  </a>*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <a className="w-100" href="assets/images/product/large-size/4.jpg">*/}
                    {/*    <img*/}
                    {/*      className="w-100"*/}
                    {/*      src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"*/}
                    {/*      alt="Product"*/}
                    {/*    />*/}
                    {/*  </a>*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <a className="w-100" href="assets/images/product/large-size/5.jpg">*/}
                    {/*    <img*/}
                    {/*      className="w-100"*/}
                    {/*      src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"*/}
                    {/*      alt="Product"*/}
                    {/*    />*/}
                    {/*  </a>*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <a className="w-100" href="assets/images/product/large-size/6.jpg">*/}
                    {/*    <img*/}
                    {/*      className="w-100"*/}
                    {/*      src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"*/}
                    {/*      alt="Product"*/}
                    {/*    />*/}
                    {/*  </a>*/}
                    {/*</div>*/}
                  </div>
                </div>
                <div className="single-product-thumb swiper-container gallery-thumbs">
                  <div className="swiper-wrapper">
                    {/*<div className="swiper-slide">*/}
                    {/*  <img src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png" alt="Product" />*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <img src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png" alt="Product" />*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <img src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png" alt="Product" />*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <img src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png" alt="Product" />*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <img src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png" alt="Product" />*/}
                    {/*</div>*/}
                    {/*<div className="swiper-slide">*/}
                    {/*  <img src="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png" alt="Product" />*/}
                    {/*</div>*/}
                  </div>
                  <div className="swiper-button-next swiper-button-white">
                    <i className="lnr lnr-arrow-right"></i>
                  </div>
                  <div className="swiper-button-prev swiper-button-white">
                    <i className="lnr lnr-arrow-left"></i>
                  </div>
                </div>
              </div>
            </div>
            <div className="col-lg-7 col-custom">
              <div className="product-summery position-relative">
                <div className="product-head mb-3">
                  <h2 className="product-title">{giftItem.giftName}</h2>
                </div>
                <div className="price-box mb-2">
                  <span className="regular-price">$ {giftItem.unitPrice}</span>
                  <span className="old-price">
                    <del>$ {giftItem.unitPrice + 100}</del>
                  </span>
                </div>
                {/*<div className="sku mb-3">*/}
                {/*  <span>SKU: 12345</span>*/}
                {/*</div>*/}
                <p className="desc-content mb-5">{}</p>
                <div className="quantity-with_btn mb-5">
                  {/*<div className="quantity">*/}
                  {/*  <div className="cart-plus-minus">*/}
                  {/*    <input className="cart-plus-minus-box" value="0" type="text" />*/}
                  {/*    /!*<div className="dec qtybutton">-</div>*!/*/}
                  {/*    /!*<div className="inc qtybutton">+</div>*!/*/}
                  {/*  </div>*/}
                  {/*</div>*/}
                  <div className="add-to_cart">
                    <Button onClick={() => onAddToCart()} className="btn product-cart button-icon flosun-button dark-btn">
                      <span className="menu-text"> Add to cart</span>
                    </Button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="row mt-no-text">
            <div className="col-lg-12 col-custom">
              <ul className="nav nav-tabs" id="myTab" role="tablist">
                <li className="nav-item">
                  <a
                    className="nav-link active text-uppercase"
                    id="home-tab"
                    data-toggle="tab"
                    href="#connect-1"
                    role="tab"
                    aria-selected="true"
                  >
                    Description
                  </a>
                </li>
              </ul>
              <div className="tab-content mb-text" id="myTabContent">
                <div className="tab-pane fade show active" id="connect-1" role="tabpanel" aria-labelledby="home-tab">
                  <div className="desc-content">
                    <p className="mb-3">{giftItem.descripption}</p>
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

const mapStateToProps = ({ giftItem, authentication }: IRootState) => ({
  giftItem: giftItem.selectedGiftItem,
  account: authentication.account,
});

const mapDispatchToProps = {
  addToCart,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftDetail);
