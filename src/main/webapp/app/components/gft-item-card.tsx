import React from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { IRootState } from 'app/shared/reducers';
import { connect } from 'react-redux';
import { Button } from 'reactstrap';
import { IGiftItem } from 'app/shared/model/gift-item.model';
import { selectGiftIem } from 'app/entities/gift-item/gift-item.reducer';
import { addToCart } from 'app/entities/cart/cart.reducer';

export interface IGftItemCardProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
  giftItem: IGiftItem;
}
const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

const GftItemCard = (props: IGftItemCardProps) => {
  const { giftItem, account } = props;

  function onProductClick() {
    props.selectGiftIem(giftItem);
  }

  function onAddToCart() {
    props.addToCart(giftItem.id, account.id);
  }

  return (
    <div onClick={() => onProductClick()} className="col-md-6 col-sm-6 col-lg-4 col-custom product-area">
      <div className="product-item">
        <div className="single-product position-relative mr-0 ml-0">
          <div className="product-image">
            <img
              src={giftItem.image != undefined ? 'http://localhost:8080/public/image/' + giftItem.image.imagepath : ''}
              alt=""
              className="product-image-1 w-100"
            />
            <img
              src={giftItem.image != undefined ? 'http://localhost:8080/public/image/' + giftItem.image.imagepath : ''}
              alt=""
              className="product-image-2 position-absolute w-100"
            />
            <span className="onsale">Sale!</span>
            <div className="add-action d-flex flex-column position-absolute">
              <Button
                onClick={() => onProductClick()}
                title="Quick View"
                data-toggle="modal"
                data-target="#exampleModalCenter"
                style={{ background: 'transparent', border: 'none' }}
                tag={Link}
                to={`${baseHref}/shop/detail`}
              >
                <i className="lnr lnr-eye" data-toggle="tooltip" data-placement="left" title="Quick View"></i>
              </Button>
            </div>
          </div>
          <div className="product-content">
            <div className="product-title">
              <h4 className="title-2">{giftItem.giftName}</h4>
            </div>
            <div className="price-box">
              <span className="regular-price ">$ {giftItem.unitPrice}</span>
              <span className="old-price">
                <del>$ {giftItem.unitPrice + 50}</del>
              </span>
            </div>
            <Button onClick={() => onAddToCart()} style={{ background: '#e72463', border: 'none' }} className="btn product-cart">
              <span className="menu-text"> Add to Cart</span>
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({ category, authentication }: IRootState) => ({
  account: authentication.account,
});

const mapDispatchToProps = {
  selectGiftIem,
  addToCart,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GftItemCard);
