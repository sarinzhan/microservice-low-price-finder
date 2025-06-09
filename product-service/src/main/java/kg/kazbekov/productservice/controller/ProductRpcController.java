package kg.kazbekov.productservice.controller;

import io.grpc.stub.StreamObserver;
import kg.kazbekov.productservice.grpc.*;
import kg.kazbekov.productservice.model.UserSubscription;
import kg.kazbekov.productservice.service.internal.ManufactureService;
import kg.kazbekov.productservice.service.internal.ModelService;
import kg.kazbekov.productservice.service.internal.ProductService;
import kg.kazbekov.productservice.service.internal.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductRpcController extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;
    private final ManufactureService manufactureService;
    private final ModelService modelService;
    private final UserSubscriptionService userSubscriptionService;

    @Override
    public void getProductByModel(ProductByModelRequest request, StreamObserver<ProductByModelResponse> responseObserver) {
        productService.getAllByModelId(request.getModelId())
                .collectList()
                .subscribe(products -> {
                    var builder = ProductByModelResponse.newBuilder();
                    products.forEach(product -> {
                        builder.addProducts(
                                Product.newBuilder()
                                        .setId(product.getId())
                                        .setPrice(product.getPrice())
                                        .setUrl(product.getUrl())
                                        .setRam(product.getRam())
                                        .setStorage(product.getStorage())
                                        .build()
                        );
                    });
                    responseObserver.onNext(builder.build());
                    responseObserver.onCompleted();
                });
    }


    @Override
    public void getAllManufacture(AllRequest request, StreamObserver<AllResponse> responseObserver) {
        manufactureService.getAll()
                .collectList()
                .subscribe(manufactures -> {
                    var builder = AllResponse.newBuilder();
                    manufactures.forEach(manufacture -> {
                        builder
                                .addManufacturies(Manufacture.newBuilder()
                                        .setId(manufacture.getId())
                                        .setName(manufacture.getName())
                                        .build()
                                );
                    });
                    responseObserver.onNext(builder.build());
                    responseObserver.onCompleted();
                });
    }

    @Override
    public void getModelsByManufacture(ModelByManufactureRequest request, StreamObserver<ModelByManufactureResponse> responseObserver) {
        modelService.findByManufactureId(request.getManufactureId())
                .flatMap(model ->
                        productService.getAllByModelId(model.getId())
                                .count()
                                .map(count -> Model.newBuilder()
                                        .setId(model.getId())
                                        .setName(model.getName())
                                        .setQuantity(count.intValue())
                                        .build()
                                )
                )
                .collectList()
                .subscribe(modelResponses -> {
                    var builder = ModelByManufactureResponse.newBuilder();
                    builder.addAllModels(modelResponses);
                    responseObserver.onNext(builder.build());
                    responseObserver.onCompleted();
                });
    }

    @Override
    public void subscribeUserOnModelPriceChange(SubscribeUserOnModelPriceChangeRequest request, StreamObserver<SubscribeUserOnModelPriceChangeResponse> responseObserver) {
        userSubscriptionService.subscribeUser(request.getModelId(), request.getChatId())
                .subscribe(
                        userSubscription -> {
                            responseObserver.onNext(
                                    SubscribeUserOnModelPriceChangeResponse.newBuilder()
                                            .setAnswer("Пользователь подписан")
                                            .build()
                            );
                            responseObserver.onCompleted();
                        },
                        responseObserver::onError
                );
    }
}
