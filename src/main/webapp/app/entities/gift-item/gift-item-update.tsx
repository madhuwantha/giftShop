import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IImage } from 'app/shared/model/image.model';
import { getEntities as getImages } from 'app/entities/image/image.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { getEntities as getCarts } from 'app/entities/cart/cart.reducer';
import { IGiftOrder } from 'app/shared/model/gift-order.model';
import { getEntities as getGiftOrders } from 'app/entities/gift-order/gift-order.reducer';
import { getEntity, updateEntity, createEntity, reset } from './gift-item.reducer';
import { IGiftItem } from 'app/shared/model/gift-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGiftItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GiftItemUpdate = (props: IGiftItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { giftItemEntity, images, categories, carts, giftOrders, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/gift-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getImages();
    props.getCategories();
    props.getCarts();
    props.getGiftOrders();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...giftItemEntity,
        ...values,
        image: images.find(it => it.id.toString() === values.imageId.toString()),
        category: categories.find(it => it.id.toString() === values.categoryId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="giftShopApp.giftItem.home.createOrEditLabel" data-cy="GiftItemCreateUpdateHeading">
            Create or edit a GiftItem
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : giftItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="gift-item-id">ID</Label>
                  <AvInput id="gift-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="giftNameLabel" for="gift-item-giftName">
                  Gift Name
                </Label>
                <AvField id="gift-item-giftName" data-cy="giftName" type="text" name="giftName" />
              </AvGroup>
              <AvGroup>
                <Label id="descripptionLabel" for="gift-item-descripption">
                  Descripption
                </Label>
                <AvField id="gift-item-descripption" data-cy="descripption" type="text" name="descripption" />
              </AvGroup>
              <AvGroup>
                <Label id="unitPriceLabel" for="gift-item-unitPrice">
                  Unit Price
                </Label>
                <AvField id="gift-item-unitPrice" data-cy="unitPrice" type="string" className="form-control" name="unitPrice" />
              </AvGroup>
              <AvGroup>
                <Label id="avalibleQuantityLabel" for="gift-item-avalibleQuantity">
                  Avalible Quantity
                </Label>
                <AvField
                  id="gift-item-avalibleQuantity"
                  data-cy="avalibleQuantity"
                  type="string"
                  className="form-control"
                  name="avalibleQuantity"
                />
              </AvGroup>
              <AvGroup>
                <Label for="gift-item-image">Image</Label>
                <AvInput id="gift-item-image" data-cy="image" type="select" className="form-control" name="imageId">
                  <option value="" key="0" />
                  {images
                    ? images.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="gift-item-category">Category</Label>
                <AvInput id="gift-item-category" data-cy="category" type="select" className="form-control" name="categoryId">
                  <option value="" key="0" />
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/gift-item" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  images: storeState.image.entities,
  categories: storeState.category.entities,
  carts: storeState.cart.entities,
  giftOrders: storeState.giftOrder.entities,
  giftItemEntity: storeState.giftItem.entity,
  loading: storeState.giftItem.loading,
  updating: storeState.giftItem.updating,
  updateSuccess: storeState.giftItem.updateSuccess,
});

const mapDispatchToProps = {
  getImages,
  getCategories,
  getCarts,
  getGiftOrders,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftItemUpdate);
