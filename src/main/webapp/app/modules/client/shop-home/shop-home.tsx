import React, { useEffect } from 'react';

import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';
import { getEntities, selectCategory } from 'app/entities/category/category.reducer';
import { getEntities as getGiftItems, getEntitiesByCategory } from 'app/entities/gift-item/gift-item.reducer';
import GftItemCard from 'app/components/gft-item-card';
import { addToCart } from 'app/entities/cart/cart.reducer';

export interface IShopHomeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ShopHome = (props: IShopHomeProps) => {
  useEffect(() => {
    props.getEntities();
    props.getGiftItems();
  }, []);

  const { categoryList, match, account } = props;

  const onCategoryClicked = (id: number) => {
    props.selectCategory(id);
    props.getEntitiesByCategory(id);
  };

  function onAllClick() {
    props.getGiftItems();
  }

  return (
    <div>
      <div className="shop-main-area">
        <div className="container container-default custom-area">
          <div className="row flex-row-reverse">
            <div className="col-lg-9 col-12 col-custom widget-mt">
              <div className="row shop_wrapper grid_3">
                {props.giftItemList.map((getGiftItem, idx) => {
                  return <GftItemCard giftItem={getGiftItem} {...props} key={idx} />;
                })}
              </div>
            </div>
            <div className="col-lg-3 col-12 col-custom">
              <aside className="sidebar_widget widget-mt">
                <div className="widget_inner">
                  <div className="widget-list widget-mb-1">
                    <h3 className="widget-title">Categories</h3>
                    <nav>
                      <ul className="mobile-menu p-0 m-0">
                        <Button onClick={() => onAllClick()} style={{ background: 'transparent', color: 'black', border: 'none' }}>
                          <span className="menu-text">All</span>
                        </Button>
                        {categoryList.map(category => {
                          return (
                            <li key={category.id} onClick={() => onCategoryClicked(category.id)} className="menu-item-has-children">
                              <Button style={{ background: 'transparent', color: 'black', border: 'none' }}>
                                <span className="menu-text">{category.categoryName}</span>
                              </Button>
                            </li>
                          );
                        })}
                      </ul>
                    </nav>
                  </div>
                </div>
              </aside>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({ category, giftItem, authentication }: IRootState) => ({
  categoryList: category.entities,
  loading: category.loading,
  selectCategory: category.selectedCategory,
  giftItemList: giftItem.entities,
  account: authentication.account,
});

const mapDispatchToProps = {
  getEntities,
  selectCategory,
  getGiftItems,
  addToCart,
  getEntitiesByCategory,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ShopHome);
